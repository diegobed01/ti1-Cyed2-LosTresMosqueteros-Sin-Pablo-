package NumberOfInversions

class NumberOfInversionsTest extends munit.FunSuite {

  test("single element has zero inversions") {
    assertEquals(
      NumberOfInversions.countInversions(List(5)),
      0L
    )
  }

  test("sorted list has zero inversions") {
    assertEquals(
      NumberOfInversions.countInversions(List(1, 2, 3, 4)),
      0L
    )
  }

  test("descending list counts every possible inversion") {
    assertEquals(
      NumberOfInversions.countInversions(List(4, 3, 2, 1)),
      6L
    )
  }

  test("assignment example counts two inversions") {
    assertEquals(
      NumberOfInversions.countInversions(List(2, 3, 9, 2, 9)),
      2L
    )
  }

  test("equal elements do not count as inversions") {
    assertEquals(
      NumberOfInversions.countInversions(List(2, 2, 2)),
      0L
    )
  }

  test("list with negative values counts inversions correctly") {
    assertEquals(
      NumberOfInversions.countInversions(List(-1, -3, 2, 0)),
      2L
    )
  }

  test("mixed list counts inversions correctly") {
    assertEquals(
      NumberOfInversions.countInversions(List(3, 1, 2)),
      2L
    )
  }

  test("merge counts cross inversions and returns sorted list") {
    assertEquals(
      NumberOfInversions.mergeAndCount(
        List(2, 5, 7),
        List(1, 3, 6)
      ),
      (List(1, 2, 3, 5, 6, 7), 6L)
    )
  }
}