package Experiment

class ExperimentRunnerTest extends munit.FunSuite {

  test("benchmark Number of Inversions") {

    val input = DataGenerator.generateDescending(20)

    val (times, average) =
      ExperimentRunner.benchmarkInversions(input)

    assertEquals(
      Benchmark.countTimesRC(times, 0),
      9
    )

    assert(average >= 0.0)
  }


  test("benchmark Closest Points") {

    val points = DataGenerator.generatePoints(20)

    val (times, average) =
      ExperimentRunner.benchmarkClosestPoints(points)

    assertEquals(
      Benchmark.countTimesRC(times, 0),
      9
    )

    assert(average >= 0.0)
  }
}