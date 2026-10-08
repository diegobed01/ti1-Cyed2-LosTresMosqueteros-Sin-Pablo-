package QuickSort

class QuickSortTest extends munit.FunSuite {

  test("sorts a list with repeated values") {
    val quickSort = new QuickSort()

    val input = List(2, 3, 9, 2, 2)
    val expected = List(2, 2, 2, 3, 9)

    val result = quickSort.quickSort(input)

    assertEquals(result, expected)
  }

  test("sorts a list with many repeated values") {
    val quickSort = new QuickSort()

    val input = List(4, 4, 1, 9, 4, 1, 4, 4)
    val expected = List(1, 1, 4, 4, 4, 4, 4, 9)

    val result = quickSort.quickSort(input)

    assertEquals(result, expected)
  }

  test("sorts an empty list") {
    val quickSort = new QuickSort()

    val input = List.empty[Int]
    val expected = List.empty[Int]

    val result = quickSort.quickSort(input)

    assertEquals(result, expected)
  }

  test("sorts a single element list") {
    val quickSort = new QuickSort()

    val input = List(5)
    val expected = List(5)

    val result = quickSort.quickSort(input)

    assertEquals(result, expected)
  }

  test("partitions elements into three groups") {
    val quickSort = new QuickSort()

    val input = List(4, 1, 9, 4, 1, 4)
    val pivot = 4

    val result = quickSort.partition(input, pivot)

    val expected = (
      List(1, 1),
      List(4, 4, 4),
      List(9)
    )

    assertEquals(result, expected)
  }

  test("partitions a list with negative values") {
    val quickSort = new QuickSort()

    val input = List(-2, 5, -2, 0, 5, -4)
    val pivot = 0

    val result = quickSort.partition(input, pivot)

    val expected = (
      List(-2, -2, -4),
      List(0),
      List(5, 5)
    )

    assertEquals(result, expected)
  }

  test("keeps all elements after sorting") {
    val quickSort = new QuickSort()

    val input = List(5, 5, 3, 3, 8, 8, 1)
    val expected = List(1, 3, 3, 5, 5, 8, 8)

    val result = quickSort.quickSort(input)

    assertEquals(result, expected)
  }
}