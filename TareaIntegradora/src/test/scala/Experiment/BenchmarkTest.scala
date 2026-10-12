package Experiment

class BenchmarkTest extends munit.FunSuite {

  test("measure execution time") {

    val time = Benchmark.measureTime {

      DataGenerator.generateAscending(1000)
    }

    assert(time >= 0.0)
  }

  test("measure operation ten times") {

    val times = Benchmark.measureTenTimes {
      DataGenerator.generateAscending(100)
    }

    assertEquals(
      Benchmark.countTimesRC(times, 0),
      10
    )
  }


  test("discard first execution") {

    assertEquals(
      Benchmark.discardFirst(
        List(1.0, 2.0, 3.0)
      ),
      List(2.0, 3.0)
    )
  }


  test("calculate average time") {

    assertEquals(
      Benchmark.averageTime(
        List(2.0, 4.0, 6.0)
      ),
      4.0
    )
  }

  test("run complete benchmark") {

    val (times, average) =
      Benchmark.runBenchmark {
        DataGenerator.generateAscending(100)
      }

    assertEquals(
      Benchmark.countTimesRC(times, 0),
      9
    )

    assert(average >= 0.0)
  }


}