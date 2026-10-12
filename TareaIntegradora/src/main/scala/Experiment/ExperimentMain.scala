package Experiment

object ExperimentMain {

  def printResults(
                    results: List[(Int, List[Double], Double)]
                  ): Unit = {

    results match {

      case Nil =>
        ()

      case head :: tail =>

        println("Size: " + head._1)
        println("Times: " + head._2)
        println("Average: " + head._3 + " ms")
        println()

        printResults(tail)
    }
  }


  def main(args: Array[String]): Unit = {

    val sizes =
      List(1000000)


    println("NUMBER OF INVERSIONS")
    println()

    val inversionResults =
      ResultCollector.collectInversionsRC(
        sizes,
        Nil
      )

    printResults(inversionResults)


    println("QUICKSORT")
    println()

    val quickSortResults =
      ResultCollector.collectQuickSortRC(
        sizes,
        Nil
      )

    printResults(quickSortResults)


    println("CLOSEST POINTS")
    println()

    val closestPointsResults =
      ResultCollector.collectClosestPointsRC(
        sizes,
        Nil
      )

    printResults(closestPointsResults)
  }
}