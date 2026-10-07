package NumberOfInversions
import scala.annotation.tailrec

object NumberOfInversions {


  @tailrec
  def listLengthRC(list: List[Int], acum: Int): Int = {

    list match {

      case Nil => acum

      case head :: tail =>
        listLengthRC(tail, acum + 1)
    }
  }


  @tailrec
  def reverseListRC(list: List[Int], acum: List[Int]): List[Int] = {

    list match {

      case Nil => acum

      case head :: tail =>
        reverseListRC(tail, head :: acum)
    }
  }

  @tailrec
  def splitListRC(
                   list: List[Int],
                   n: Int,
                   accumulator: List[Int]
                 ): (List[Int], List[Int]) = {

    list match {

      case Nil =>
        (reverseListRC(accumulator, Nil), Nil)

      case head :: tail =>

        if (n == 0) {
          (reverseListRC(accumulator, Nil), list)
        } else {
          splitListRC(
            tail,
            n - 1,
            head :: accumulator
          )
        }
    }
  }

  def mergeAndCount(
                     left: List[Int],
                     right: List[Int]
                   ): (List[Int], Long) = {

    @tailrec
    def mergeAndCountRC(
                         leftRemaining: List[Int],
                         rightRemaining: List[Int],
                         leftSize: Int,
                         acum: List[Int],
                         inversions: Long
                       ): (List[Int], Long) = {

      (leftRemaining, rightRemaining) match {

        case (Nil, Nil) =>
          (reverseListRC(acum, Nil), inversions)


        case (Nil, headRight :: tailRight) =>
          mergeAndCountRC(
            Nil,
            tailRight,
            0,
            headRight :: acum,
            inversions
          )


        case (headLeft :: tailLeft, Nil) =>
          mergeAndCountRC(
            tailLeft,
            Nil,
            leftSize - 1,
            headLeft :: acum,
            inversions
          )


        case (headLeft :: tailLeft, headRight :: tailRight) =>

          if (headLeft <= headRight) {

            mergeAndCountRC(
              tailLeft,
              rightRemaining,
              leftSize - 1,
              headLeft :: acum,
              inversions
            )

          } else {

            mergeAndCountRC(
              leftRemaining,
              tailRight,
              leftSize,
              headRight :: acum,
              inversions + leftSize.toLong
            )
          }
      }
    }

    mergeAndCountRC(
      left,
      right,
      listLengthRC(left, 0),
      Nil,
      0L
    )
  }


  def mergeSortAndCount(
                         list: List[Int]
                       ): (List[Int], Long) = {

    list match {

      case Nil =>
        (Nil, 0L)

      case head :: Nil =>
        (List(head), 0L)

      case head :: tail =>

        val middle = listLengthRC(list, 0) / 2

        val (left, right) =
          splitListRC(list, middle, Nil)

        val (sortedLeft, leftInversions) =
          mergeSortAndCount(left)

        val (sortedRight, rightInversions) =
          mergeSortAndCount(right)

        val (mergedList, mergeInversions) =
          mergeAndCount(sortedLeft, sortedRight)

        (
          mergedList,
          leftInversions + rightInversions + mergeInversions
        )
    }
  }

  def countInversions(
                       list: List[Int]
                     ): Long = {

    val (sortedList, inversions) =
      mergeSortAndCount(list)

    inversions
  }

}