package Experiment

import scala.annotation.tailrec

object DataGenerator {

  val toySize: Int = 50
  val smallSize: Int = 1000
  val mediumSize: Int = 50000
  val largeSize: Int = 1000000


  /**
   * Reverses a list using tail recursion
   *
   * @param list the list to reverse
   * @param accumulator the partially reversed list
   * @return the reversed list
   */
  @tailrec
  def reverseListRC(list: List[Int], accumulator: List[Int]): List[Int] = {

    list match {

      case Nil =>
        accumulator

      case head :: tail =>
        reverseListRC(
          tail,
          head :: accumulator
        )
    }
  }


  /**
   * Generates an ascending list from 1 to n
   *
   * @param n number of elements to generate
   * @param current current value
   * @param accumulator generated elements
   * @return an ascending list containing n elements
   */
  @tailrec
  def generateAscendingRC(n: Int, current: Int, accumulator: List[Int]): List[Int] = {

    if (current > n) {
      reverseListRC(accumulator, Nil)
    } else {
      generateAscendingRC(
        n,
        current + 1,
        current :: accumulator
      )
    }
  }


  /**
   * Generates an ascending list containing n elements
   *
   * @param n number of elements to generate
   * @return an ascending list
   */
  def generateAscending(n: Int): List[Int] = {

    generateAscendingRC(
      n,
      1,
      Nil
    )
  }


  /**
   * Generates a descending list from n to 1 using tail recursion
   *
   * @param n number of elements to generate
   * @param current current value
   * @param accumulator generated elements
   * @return a descending list containing n elements
   */
  @tailrec
  def generateDescendingRC(n: Int, current: Int, accumulator: List[Int]): List[Int] = {

    if (current > n) {
      accumulator
    } else {
      generateDescendingRC(
        n,
        current + 1,
        current :: accumulator
      )
    }
  }


  /**
   * Generates a descending list containing n elements
   *
   * @param n number of elements to generate
   * @return a descending list
   */
  def generateDescending(n: Int): List[Int] = {

    generateDescendingRC(
      n,
      1,
      Nil
    )
  }


  /**
   * Generates a list with the same repeated value using tail recursion.
   *
   * @param n number of elements to generate
   * @param value value to repeat
   * @param accumulator generated elements
   * @return a list containing the repeated value
   */
  @tailrec
  def generateRepeatedRC(
                          n: Int,
                          value: Int,
                          accumulator: List[Int]
                        ): List[Int] = {

    if (n == 0) {
      accumulator
    } else {
      generateRepeatedRC(
        n - 1,
        value,
        value :: accumulator
      )
    }
  }


  /**
   * Generates a list with n repeated values
   *
   * @param n number of elements to generate
   * @param value value to repeat
   * @return a list containing n repetitions of value
   */
  def generateRepeated(n: Int, value: Int): List[Int] = {

    generateRepeatedRC(
      n,
      value,
      Nil
    )
  }
}