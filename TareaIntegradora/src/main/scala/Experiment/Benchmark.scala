package Experiment

import scala.annotation.tailrec

object Benchmark {

  /**
   * Measures the execution time of an operation
   *
   * @param operation the operation to execute
   * @return the execution time in milliseconds
   */
  def measureTime(operation: => Unit): Double = {

    val startTime = System.nanoTime()

    operation

    val endTime = System.nanoTime()

    (endTime - startTime) / 1000000.0
  }


  /**
   * Reverses a list of execution times using tail recursion
   *
   * @param list the list to reverse
   * @param accumulator the partially reversed list
   * @return the reversed list
   */
  @tailrec
  def reverseTimesRC(list: List[Double], accumulator: List[Double]): List[Double] = {

    list match {

      case Nil => accumulator

      case head :: tail => reverseTimesRC(tail, head :: accumulator)
    }
  }


  /**
   * Measures an operation several times using tail recursion
   *
   * @param repetitions number of executions remaining
   * @param operation the operation to measure
   * @param accumulator execution times obtained so far
   * @return the execution times in milliseconds
   */
  @tailrec
  def measureRepeatedRC(repetitions: Int, operation: => Unit, accumulator: List[Double]): List[Double] = {

    if (repetitions == 0) {

      reverseTimesRC(accumulator, Nil)

    } else {

      val time = measureTime {
        operation
      }

      measureRepeatedRC(repetitions - 1, operation, time :: accumulator)
    }
  }


  /**
   * Measures an operation ten times
   *
   * @param operation the operation to measure
   * @return the ten execution times
   */
  def measureTenTimes(operation: => Unit): List[Double] = {

    measureRepeatedRC(10, operation, Nil)
  }


  /**
   * Removes the first execution time
   *
   * @param times the execution times
   * @return the execution times without the first measurement
   */
  def discardFirst(times: List[Double]): List[Double] = {

    times match {

      case Nil => Nil

      case head :: tail => tail
    }
  }


  /**
   * Adds execution times using tail recursion
   *
   * @param times the execution times
   * @param accumulator the accumulated time
   * @return the total execution time
   */
  @tailrec
  def sumTimesRC(times: List[Double], accumulator: Double): Double = {

    times match {

      case Nil => accumulator

      case head :: tail => sumTimesRC(tail, accumulator + head)
    }
  }


  /**
   * Counts execution times using tail recursion
   *
   * @param times the execution times
   * @param accumulator the number of times counted
   * @return the number of execution times
   */
  @tailrec
  def countTimesRC(times: List[Double], accumulator: Int): Int = {

    times match {

      case Nil => accumulator

      case head :: tail => countTimesRC(tail, accumulator + 1)
    }
  }


  /**
   * Calculates the average execution time
   *
   * @param times the execution times
   * @return the average time in milliseconds
   */
  def averageTime(times: List[Double]): Double = {

    times match {

      case Nil => 0.0

      case head :: tail =>

        val total = sumTimesRC(times, 0.0)

        val count = countTimesRC(times, 0)

        total / count
    }
  }


  /**
   * Runs an operation ten times, discards the first measurement
   * and calculates the average of the remaining times
   *
   * @param operation the operation to measure
   * @return the execution times used and their average
   */
  def runBenchmark(operation: => Unit): (List[Double], Double) = {

    val allTimes = measureTenTimes(operation)

    val validTimes = discardFirst(allTimes)

    val average = averageTime(validTimes)

    (validTimes, average)
  }
}