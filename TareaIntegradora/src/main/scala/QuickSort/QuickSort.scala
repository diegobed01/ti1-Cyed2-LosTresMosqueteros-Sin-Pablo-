package QuickSort

class QuickSort {

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

    def quickSort(list: List[Int]): List[Int] = {

      list match {

        case Nil =>
          List()

        case pivot :: tail =>

          val (less, equal, greater) = partition(tail, pivot)

          quickSort(less) ++ equal ++ quickSort(greater)
      }
    }
  }

