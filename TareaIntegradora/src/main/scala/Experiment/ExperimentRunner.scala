package Experiment

import NumberOfInversions.NumberOfInversions
import ClosestPoints.ClosestPoints

object ExperimentRunner {

  /**
   * Runs the benchmark for the Number of Inversions algorithm
   *
   * @param input the list to analyze
   * @return the execution times and their average
   */
  def benchmarkInversions(input: List[Int]): (List[Double], Double) = {

    Benchmark.runBenchmark {
      NumberOfInversions.countInversions(input)
    }
  }


  /**
   * Runs the benchmark for the Closest Points algorithm
   *
   * @param points the points to analyze
   * @return the execution times and their average
   */
  def benchmarkClosestPoints(points: List[List[Int]]): (List[Double], Double) = {

    Benchmark.runBenchmark {
      ClosestPoints.closestDistance(points)
    }
  }
}