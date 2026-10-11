package ClosestPoints
import scala.annotation.tailrec

/**
 * Solves the closest pair of points problem using the divide and conquer
 * strategy in O(n log n) time.
 *
 * The recursive function returns two values: the smallest squared distance
 * found and the same points sorted by y. Returning the points already sorted
 * by y lets every level build its strip with a linear merge instead of
 * sorting it again, which keeps the combine step in O(n) and gives the
 * recurrence T(n) = 2T(n/2) + O(n).
 *
 * All internal distances are squared distances stored as Long values, so the
 * comparisons are exact. The square root is only taken once, at the end.
 * Coordinates are assumed to satisfy |x|, |y| <= 10^9, which guarantees that
 * dx^2 + dy^2 <= 8 * 10^18 fits in a Long.
 */
object ClosestPoints {

  /** A point of the plane represented as an immutable pair (x, y). */
  type Point = (Int, Int)

  /** Squared distance used when a set has no pairs (acts as infinity). */
  val Infinity: Long = Long.MaxValue

  // ---------------------------------------------------------------------
  // Basic list utilities
  // ---------------------------------------------------------------------

  /**
   * Calculates the length of a list of points using tail recursion.
   *
   * @param list the list to process
   * @param acum the number of elements counted so far
   * @return the total number of elements in the list
   */
  @tailrec
  def listLengthRC(list: List[Point], acum: Int): Int = {
    list match {
      case Nil => acum
      case _ :: tail => listLengthRC(tail, acum + 1)
    }
  }

  /**
   * Reverses a list of points using tail recursion.
   *
   * @param list the list to reverse
   * @param acum the partially reversed list
   * @return the reversed list
   */
  @tailrec
  def reverseListRC(list: List[Point], acum: List[Point]): List[Point] = {
    list match {
      case Nil => acum
      case head :: tail => reverseListRC(tail, head :: acum)
    }
  }

  /**
   * Splits a list into its first n elements and the remaining elements
   * using tail recursion.
   *
   * @param list the list to split
   * @param n    the number of elements to place in the first part
   * @param acum the elements collected so far for the first part (reversed)
   * @return a pair with the first n elements and the rest of the list
   */
  @tailrec
  def splitListRC(
                   list: List[Point],
                   n: Int,
                   acum: List[Point]
                 ): (List[Point], List[Point]) = {
    list match {
      case Nil =>
        (reverseListRC(acum, Nil), Nil)
      case head :: tail =>
        if (n == 0) (reverseListRC(acum, Nil), list)
        else splitListRC(tail, n - 1, head :: acum)
    }
  }

  /**
   * Converts the raw input (each point as a list of two integers) into
   * points, validating that every element has exactly two coordinates.
   * The resulting order is reversed, which is irrelevant because the
   * points are sorted by x afterwards.
   *
   * @param raw  the remaining raw points
   * @param acum the points converted so far
   * @return the converted points
   * @throws IllegalArgumentException if a point does not have two coordinates
   */
  @tailrec
  def toPointsRC(raw: List[List[Int]], acum: List[Point]): List[Point] = {
    raw match {
      case Nil => acum
      case List(x, y) :: tail => toPointsRC(tail, (x, y) :: acum)
      case _ :: _ =>
        throw new IllegalArgumentException(
          "Every point must have exactly two coordinates"
        )
    }
  }

  // ---------------------------------------------------------------------
  // Geometry
  // ---------------------------------------------------------------------

  /**
   * Computes the squared Euclidean distance between two points.
   * The differences are converted to Long before multiplying to avoid
   * Int overflow.
   *
   * @param p the first point
   * @param q the second point
   * @return (xp - xq)^2 + (yp - yq)^2 as a Long
   */
  def distanceSquared(p: Point, q: Point): Long = {
    val dx = p._1.toLong - q._1.toLong
    val dy = p._2.toLong - q._2.toLong
    dx * dx + dy * dy
  }

  /**
   * Returns the smaller of two Long values.
   *
   * @param a the first value
   * @param b the second value
   * @return the minimum between a and b
   */
  def minLong(a: Long, b: Long): Long = if (a <= b) a else b

  /**
   * Rounds a non-negative value to at most four decimal digits using
   * the HALF_UP rounding mode.
   *
   * @param value the value to round
   * @return the value rounded to four decimal digits
   */
  def roundTo4(value: Double): Double =
    BigDecimal(value).setScale(4, BigDecimal.RoundingMode.HALF_UP).toDouble

  // ---------------------------------------------------------------------
  // Sorting by x (initial step)
  // ---------------------------------------------------------------------

  /**
   * Merges two lists sorted by x (ties broken by y) into one sorted list
   * using tail recursion. The accumulator is built in reverse order and
   * reversed once at the end.
   *
   * @param left  the remaining elements of the left sorted list
   * @param right the remaining elements of the right sorted list
   * @param acum  the merged elements so far, in reverse order
   * @return the merged list sorted by x
   */
  @tailrec
  def mergeByXRC(
                  left: List[Point],
                  right: List[Point],
                  acum: List[Point]
                ): List[Point] = {
    (left, right) match {
      case (Nil, Nil) =>
        reverseListRC(acum, Nil)
      case (Nil, headRight :: tailRight) =>
        mergeByXRC(Nil, tailRight, headRight :: acum)
      case (headLeft :: tailLeft, Nil) =>
        mergeByXRC(tailLeft, Nil, headLeft :: acum)
      case (headLeft :: tailLeft, headRight :: tailRight) =>
        if (headLeft._1 < headRight._1 ||
          (headLeft._1 == headRight._1 && headLeft._2 <= headRight._2))
          mergeByXRC(tailLeft, right, headLeft :: acum)
        else
          mergeByXRC(left, tailRight, headRight :: acum)
    }
  }

  /**
   * Sorts a list of points by x using merge sort.
   *
   * @param list the list to sort
   * @param n    the number of elements of the list
   * @return the list sorted by x (ties broken by y)
   */
  def sortByX(list: List[Point], n: Int): List[Point] = {
    list match {
      case Nil => Nil
      case _ :: Nil => list
      case _ =>
        val half = n / 2
        val (left, right) = splitListRC(list, half, Nil)
        mergeByXRC(sortByX(left, half), sortByX(right, n - half), Nil)
    }
  }

  // ---------------------------------------------------------------------
  // Combine step
  // ---------------------------------------------------------------------

  /**
   * Merges two lists sorted by y into one list sorted by y using tail
   * recursion. This is the step that keeps the combine phase linear.
   *
   * @param left  the remaining elements of the left list sorted by y
   * @param right the remaining elements of the right list sorted by y
   * @param acum  the merged elements so far, in reverse order
   * @return the merged list sorted by y
   */
  @tailrec
  def mergeByYRC(
                  left: List[Point],
                  right: List[Point],
                  acum: List[Point]
                ): List[Point] = {
    (left, right) match {
      case (Nil, Nil) =>
        reverseListRC(acum, Nil)
      case (Nil, headRight :: tailRight) =>
        mergeByYRC(Nil, tailRight, headRight :: acum)
      case (headLeft :: tailLeft, Nil) =>
        mergeByYRC(tailLeft, Nil, headLeft :: acum)
      case (headLeft :: tailLeft, headRight :: tailRight) =>
        if (headLeft._2 <= headRight._2)
          mergeByYRC(tailLeft, right, headLeft :: acum)
        else
          mergeByYRC(left, tailRight, headRight :: acum)
    }
  }

  /**
   * Keeps the points whose squared horizontal distance to the dividing
   * line is strictly smaller than the current best squared distance.
   * The input is sorted by y, and the result is returned in reverse order
   * (sorted by decreasing y); the caller reverses it once.
   *
   * @param points the remaining points, sorted by y
   * @param midX   the x coordinate of the dividing line
   * @param best   the current best squared distance d^2
   * @param acum   the strip points collected so far, in reverse order
   * @return the strip points in reverse order
   */
  @tailrec
  def buildStripRC(
                    points: List[Point],
                    midX: Int,
                    best: Long,
                    acum: List[Point]
                  ): List[Point] = {
    points match {
      case Nil => acum
      case head :: tail =>
        val dx = head._1.toLong - midX.toLong
        if (dx * dx < best) buildStripRC(tail, midX, best, head :: acum)
        else buildStripRC(tail, midX, best, acum)
    }
  }

  /**
   * Compares a point with the following points of the strip while their
   * vertical distance can still improve the best distance. The geometric
   * packing argument guarantees that at most 7 points are visited.
   *
   * @param p      the point being compared
   * @param others the strip points above p, sorted by y
   * @param best   the best squared distance found so far
   * @return the updated best squared distance
   */
  @tailrec
  def scanNeighborsRC(p: Point, others: List[Point], best: Long): Long = {
    others match {
      case Nil => best
      case q :: tail =>
        val dy = q._2.toLong - p._2.toLong
        if (dy * dy >= best) best
        else scanNeighborsRC(p, tail, minLong(best, distanceSquared(p, q)))
    }
  }

  /**
   * Scans the strip sorted by y, comparing every point with its close
   * neighbors above it.
   *
   * @param strip the remaining strip points, sorted by y
   * @param best  the best squared distance found so far
   * @return the smallest squared distance found in the strip, or best if
   *         no pair improves it
   */
  @tailrec
  def scanStripRC(strip: List[Point], best: Long): Long = {
    strip match {
      case Nil => best
      case p :: tail => scanStripRC(tail, scanNeighborsRC(p, tail, best))
    }
  }

  // ---------------------------------------------------------------------
  // Divide and conquer
  // ---------------------------------------------------------------------

  /**
   * Recursive divide and conquer step.
   *
   * Splits the points (sorted by x) into two halves, solves both halves,
   * merges their y-sorted lists, builds the strip around the dividing line
   * and scans it to find the closest cross pair.
   *
   * @param byX the points sorted by x (non-empty)
   * @param n   the number of points in byX (n >= 1)
   * @return a pair with the smallest squared distance among the points
   *         (Infinity if n = 1) and the same points sorted by y
   */
  def closestRec(byX: List[Point], n: Int): (Long, List[Point]) = {
    byX match {
      case _ :: Nil =>
        (Infinity, byX)
      case _ =>
        val half = n / 2
        val (left, right) = splitListRC(byX, half, Nil)
        val midX = right match {
          case first :: _ => first._1
          case Nil => throw new IllegalStateException("Right half is empty")
        }
        val (d1, leftByY) = closestRec(left, half)
        val (d2, rightByY) = closestRec(right, n - half)
        val d = minLong(d1, d2)
        val allByY = mergeByYRC(leftByY, rightByY, Nil)
        val strip = reverseListRC(buildStripRC(allByY, midX, d, Nil), Nil)
        (scanStripRC(strip, d), allByY)
    }
  }

  /**
   * Computes the smallest Euclidean distance between two different points
   * of the input, rounded to at most four decimal digits.
   *
   * @param points the input points, each one a list of two integers
   *               (n >= 2, |x|, |y| <= 10^9)
   * @return the minimum distance rounded to four decimal digits
   * @throws IllegalArgumentException if there are fewer than two points or
   *                                  a point does not have two coordinates
   */
  def closestDistance(points: List[List[Int]]): Double = {
    val parsed = toPointsRC(points, Nil)
    val n = listLengthRC(parsed, 0)
    if (n < 2)
      throw new IllegalArgumentException("At least two points are required")
    val (best, _) = closestRec(sortByX(parsed, n), n)
    roundTo4(math.sqrt(best.toDouble))
  }
}
