package QuickSort

class QuickSort {

  /**
   * Partitions a list into elements smaller than, equal to,
   * and greater than the pivot.
   *
   * @param list the list to partition
   * @param pivot the pivot value
   * @return three lists containing elements less than, equal to,
   *         and greater than the pivot
   */
  def partition(list: List[Int], pivot: Int):
  (List[Int], List[Int], List[Int]) = {

    list match {

      case Nil =>
        (List(), List(), List())

      case head :: tail =>

        val (less, equal, greater) = partition(tail, pivot)

        if (head < pivot) {
          (head :: less, equal, greater)
        } else if (head == pivot) {
          (less, head :: equal, greater)
        } else {
          (less, equal, head :: greater)
        }
    }
  }

  /**
   * Sorts a list using QuickSort with a three-way partition.
   *
   * @param list the list to sort
   * @return the sorted list
   */
  def quickSort(list: List[Int]): List[Int] = {

    list match {

      case Nil =>
        List()

      case pivot :: tail =>

        val (less, equal, greater) = partition(tail, pivot)

        quickSort(less) ++ (pivot :: equal) ++ quickSort(greater)
    }
  }
}