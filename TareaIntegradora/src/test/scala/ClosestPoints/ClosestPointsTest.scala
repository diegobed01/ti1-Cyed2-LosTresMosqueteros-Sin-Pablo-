package ClosestPoints
import scala.annotation.tailrec

class ClosestPointsTest extends munit.FunSuite {

  // ---------------------------------------------------------------------
  // Test helpers (brute force oracle and pseudo-random input generator)
  // ---------------------------------------------------------------------

  /**
   * Smallest squared distance between a point and every point of a list.
   * Used only by the brute force oracle of the tests.
   *
   * @param p      the reference point
   * @param others the points to compare with p
   * @param best   the best squared distance found so far
   * @return the updated best squared distance
   */
  @tailrec
  private def bruteNeighborsRC(
                                p: ClosestPoints.Point,
                                others: List[ClosestPoints.Point],
                                best: Long
                              ): Long = {
    others match {
      case Nil => best
      case q :: tail =>
        bruteNeighborsRC(
          p,
          tail,
          ClosestPoints.minLong(best, ClosestPoints.distanceSquared(p, q))
        )
    }
  }

  /**
   * Brute force O(n^2) oracle: compares every pair of points.
   * It exists only in the tests to validate the divide and conquer result.
   *
   * @param points the remaining points
   * @param best   the best squared distance found so far
   * @return the smallest squared distance among all pairs
   */
  @tailrec
  private def bruteForceRC(points: List[ClosestPoints.Point], best: Long): Long = {
    points match {
      case Nil => best
      case p :: tail => bruteForceRC(tail, bruteNeighborsRC(p, tail, best))
    }
  }

  /**
   * Generates n pseudo-random points with a linear congruential generator,
   * so the tests are deterministic without using scala.util.Random.
   * Coordinates lie in the range [-range/2, range/2).
   *
   * @param n     the number of points left to generate
   * @param seed  the current state of the generator
   * @param range the size of the coordinate range
   * @param acum  the points generated so far
   * @return the generated points as lists of two integers
   */
  @tailrec
  private def randomPointsRC(
                              n: Int,
                              seed: Long,
                              range: Int,
                              acum: List[List[Int]]
                            ): List[List[Int]] = {
    if (n == 0) acum
    else {
      val s1 = (seed * 6364136223846793005L + 1442695040888963407L)
      val s2 = (s1 * 6364136223846793005L + 1442695040888963407L)
      val x = (((s1 >>> 33) % range) - range / 2).toInt
      val y = (((s2 >>> 33) % range) - range / 2).toInt
      randomPointsRC(n - 1, s2, range, List(x, y) :: acum)
    }
  }

  /**
   * Checks the divide and conquer result against the brute force oracle.
   *
   * @param raw the input points
   */
  private def assertMatchesBruteForce(raw: List[List[Int]]): Unit = {
    val points = ClosestPoints.toPointsRC(raw, Nil)
    val expected = ClosestPoints.roundTo4(
      math.sqrt(bruteForceRC(points, ClosestPoints.Infinity).toDouble)
    )
    assertEquals(ClosestPoints.closestDistance(raw), expected)
  }

  // ---------------------------------------------------------------------
  // Main function: closestDistance
  // ---------------------------------------------------------------------

  test("assignment example 1: two points") {
    assertEquals(
      ClosestPoints.closestDistance(List(List(0, 0), List(3, 4))),
      5.0
    )
  }

  test("assignment example 2: odd number of points") {
    assertEquals(
      ClosestPoints.closestDistance(List(List(0, 0), List(3, 4), List(1, 1))),
      1.4142
    )
  }

  test("duplicated points have distance zero") {
    assertEquals(
      ClosestPoints.closestDistance(List(List(1, 1), List(5, 5), List(1, 1))),
      0.0
    )
  }

  test("negative coordinates") {
    assertEquals(
      ClosestPoints.closestDistance(List(List(-5, -5), List(-4, -4), List(10, 10))),
      1.4142
    )
  }

  test("all points share the same x coordinate") {
    assertEquals(
      ClosestPoints.closestDistance(
        List(List(0, 0), List(0, 5), List(0, 2), List(0, 9))
      ),
      2.0
    )
  }

  test("closest pair crosses the dividing line") {
    assertEquals(
      ClosestPoints.closestDistance(
        List(List(-10, 0), List(-1, 0), List(1, 0), List(10, 0))
      ),
      2.0
    )
  }

  test("grid with several pairs at the minimum distance") {
    assertEquals(
      ClosestPoints.closestDistance(
        List(List(0, 0), List(0, 1), List(1, 0), List(1, 1))
      ),
      1.0
    )
  }

  test("result is rounded half up to four decimals") {
    assertEquals(
      ClosestPoints.closestDistance(List(List(0, 0), List(1, 2))),
      2.2361
    )
  }

  test("squared differences larger than Int do not overflow") {
    assertEquals(
      ClosestPoints.closestDistance(List(List(0, 0), List(50000, 50000))),
      70710.6781
    )
  }

  test("extreme coordinates within the documented limit") {
    assertEquals(
      ClosestPoints.closestDistance(
        List(List(-1000000000, -1000000000), List(1000000000, 1000000000))
      ),
      2828427124.7462
    )
  }

  test("fewer than two points is rejected") {
    intercept[IllegalArgumentException] {
      ClosestPoints.closestDistance(List(List(3, 4)))
    }
  }

  test("a point without exactly two coordinates is rejected") {
    intercept[IllegalArgumentException] {
      ClosestPoints.closestDistance(List(List(1, 2), List(3)))
    }
  }

  test("matches brute force on 200 pseudo-random points") {
    assertMatchesBruteForce(randomPointsRC(200, 42L, 2000, Nil))
  }

  test("matches brute force on 1000 points with many repeated coordinates") {
    assertMatchesBruteForce(randomPointsRC(1000, 7L, 60, Nil))
  }

  test("matches brute force on 1000 widely spread points") {
    assertMatchesBruteForce(randomPointsRC(1000, 2026L, 2000000, Nil))
  }

  // ---------------------------------------------------------------------
  // Auxiliary functions
  // ---------------------------------------------------------------------

  test("sortByX sorts by x and breaks ties by y") {
    assertEquals(
      ClosestPoints.sortByX(List((3, 1), (1, 5), (1, 2), (2, 0)), 4),
      List((1, 2), (1, 5), (2, 0), (3, 1))
    )
  }

  test("mergeByYRC merges two lists sorted by y") {
    assertEquals(
      ClosestPoints.mergeByYRC(List((5, 1), (0, 4)), List((2, 2), (9, 7)), Nil),
      List((5, 1), (2, 2), (0, 4), (9, 7))
    )
  }

  test("buildStripRC keeps only points closer than d to the line") {
    // midX = 0, d^2 = 4: keeps |x| < 2, returned in reverse order
    assertEquals(
      ClosestPoints.buildStripRC(List((-3, 0), (1, 1), (0, 2), (2, 3)), 0, 4L, Nil),
      List((0, 2), (1, 1))
    )
  }

  test("closestRec returns the points sorted by y") {
    val (best, byY) = ClosestPoints.closestRec(List((0, 9), (1, 3), (2, 6)), 3)
    assertEquals(best, 10L)
    assertEquals(byY, List((1, 3), (2, 6), (0, 9)))
  }

  test("closestRec base case: a single point has no pairs") {
    assertEquals(
      ClosestPoints.closestRec(List((4, 4)), 1),
      (ClosestPoints.Infinity, List((4, 4)))
    )
  }
}
