package QuickSort

import scala.annotation.tailrec

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
  def partition(list: List[Int], pivot: Int): (List[Int], List[Int], List[Int]) = {

    @tailrec
    def partitionAux(list: List[Int], less: List[Int], equal: List[Int], greater: List[Int]): (List[Int], List[Int], List[Int]) = {

      list match {
        case Nil =>
          (less.reverse, equal.reverse, greater.reverse)

        case head :: tail =>
          if (head < pivot) {
            partitionAux(tail, head :: less, equal, greater)
          } else if (head == pivot) {
            partitionAux(tail, less, head :: equal, greater)
          } else {
            partitionAux(tail, less, equal, head :: greater)
          }
      }
    }

    partitionAux(list, List(), List(), List())
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