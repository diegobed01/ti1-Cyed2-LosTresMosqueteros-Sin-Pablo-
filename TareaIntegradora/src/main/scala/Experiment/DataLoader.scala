package Experiment

import scala.io.Source

object DataLoader {

  /**
   * Converts a list of strings into a list of integers
   *
   * @param lines lines containing integer values
   * @return a list of integers
   */
  def parseIntegers(lines: List[String]): List[Int] = {

    lines match {

      case Nil =>
        Nil

      case head :: tail =>
        head.toInt :: parseIntegers(tail)
    }
  }


  /**
   * Loads a list of integers from a text file
   *
   * @param filePath path of the file to read
   * @return a list containing the integers from the file
   */
  def loadIntegers(filePath: String): List[Int] = {

    val source = Source.fromFile(filePath)

    try {

      val lines = source.getLines().toList

      parseIntegers(lines)

    } finally {

      source.close()
    }
  }
}