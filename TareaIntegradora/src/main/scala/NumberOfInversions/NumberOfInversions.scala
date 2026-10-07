package NumberOfInversions
import scala.annotation.tailrec

object NumberOfInversions {


  /**
   * Calculates the length of a list using tail recursion
   *
   * @param list        the list to process
   * @param acum the number of elements counted so far
   * @return the total number of elements in the list
   */

  @tailrec
  def listLengthRC(list: List[Int], acum: Int): Int = {

    list match {

      case Nil => acum

      case head :: tail =>
        listLengthRC(tail, acum + 1)
    }
  }


  /**
   * Reverses a list using tail recursion
   *
   * @param list        the list to reverse
   * @param acum the partially reversed list
   * @return the reversed list
   */

  @tailrec
  def reverseListRC(list: List[Int], acum: List[Int]): List[Int] = {

    list match {

      case Nil => acum

      case head :: tail =>
        reverseListRC(tail, head :: acum)
    }
  }

  /**
   * Splits a list into two parts at a given position using tail recursion
   *
   * @param list        the list to split
   * @param n           the number of elements to place in the first part
   * @param accumulator the elements collected for the first part
   * @return a pair containing the two resulting lists
   */

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

  /**
   * Merges two sorted lists and counts the inversions between them
   *
   * @param left  the sorted left list
   * @param right the sorted right list
   * @return a pair containing the merged sorted list and the number of inversions
   */

  def mergeAndCount(
                     left: List[Int],
                     right: List[Int]
                   ): (List[Int], Long) = {

    /**
     * Performs the merge process using tail recursion
     *
     * @param leftRemaining  elements remaining in the left list
     * @param rightRemaining elements remaining in the right list
     * @param leftSize       number of elements remaining in the left list
     * @param acum    partially merged list
     * @param inversions     inversions counted so far
     * @return the merged list and the inversion count
     */

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


  /**
   * Sorts a list using merge sort while counting all its inversions
   *
   * @param list the list to sort and analyze
   * @return a pair containing the sorted list and the total number of inversions
   */

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

  /**
   * Calculates the total number of inversions in a list
   *
   * @param list the list to analyze
   * @return the total number of inversions
   */

  def countInversions(
                       list: List[Int]
                     ): Long = {

    val (sortedList, inversions) =
      mergeSortAndCount(list)

    inversions
  }

}