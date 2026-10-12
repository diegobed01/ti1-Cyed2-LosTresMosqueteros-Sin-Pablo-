package Experiment

import scala.annotation.tailrec

object ResultCollector {

  /**
   * Reverses a list of experimental results using tail recursion
   *
   * @param list the list of results to reverse
   * @param accumulator the partially reversed list
   * @return the reversed list of results
   */
  @tailrec
  def reverseResultsRC(
                        list: List[(Int, List[Double], Double)],
                        accumulator: List[(Int, List[Double], Double)]
                      ): List[(Int, List[Double], Double)] = {

    list match {

      case Nil => accumulator

      case head :: tail => reverseResultsRC(tail, head :: accumulator)
    }
  }


  /**
   * Collects experimental results for Number of Inversions
   *
   * @param sizes the input sizes to test
   * @param accumulator the results collected so far
   * @return the results for every input size
   */
  @tailrec
  def collectInversionsRC(
                           sizes: List[Int],
                           accumulator: List[(Int, List[Double], Double)]
                         ): List[(Int, List[Double], Double)] = {

    sizes match {

      case Nil =>
        reverseResultsRC(accumulator, Nil)

      case head :: tail =>

        val input = DataGenerator.generateDescending(head)

        val (times, average) =
          ExperimentRunner.benchmarkInversions(input)

        collectInversionsRC(
          tail,
          (head, times, average) :: accumulator
        )
    }
  }


  /**
   * Collects experimental results for QuickSort
   *
   * @param sizes the input sizes to test
   * @param accumulator the results collected so far
   * @return the results for every input size
   */
  @tailrec
  def collectQuickSortRC(
                          sizes: List[Int],
                          accumulator: List[(Int, List[Double], Double)]
                        ): List[(Int, List[Double], Double)] = {

    sizes match {

      case Nil =>
        reverseResultsRC(accumulator, Nil)

      case head :: tail =>

        val input =
          DataGenerator.generateRepeated(head, 5)

        val (times, average) =
          ExperimentRunner.benchmarkQuickSort(input)

        collectQuickSortRC(
          tail,
          (head, times, average) :: accumulator
        )
    }
  }


  /**
   * Collects experimental results for Closest Points
   *
   * @param sizes the input sizes to test
   * @param accumulator the results collected so far
   * @return the results for every input size
   */
  @tailrec
  def collectClosestPointsRC(
                              sizes: List[Int],
                              accumulator: List[(Int, List[Double], Double)]
                            ): List[(Int, List[Double], Double)] = {

    sizes match {

      case Nil =>
        reverseResultsRC(accumulator, Nil)

      case head :: tail =>

        val input =
          DataGenerator.generatePoints(head)

        val (times, average) =
          ExperimentRunner.benchmarkClosestPoints(input)

        collectClosestPointsRC(
          tail,
          (head, times, average) :: accumulator
        )
    }
  }
}