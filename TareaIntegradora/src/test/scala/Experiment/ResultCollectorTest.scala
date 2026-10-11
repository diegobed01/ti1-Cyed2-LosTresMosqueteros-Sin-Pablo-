package Experiment

class ResultCollectorTest extends munit.FunSuite {

  test("collect Number of Inversions results") {

    val results =
      ResultCollector.collectInversionsRC(
        List(10, 20),
        Nil
      )

    results match {

      case first :: second :: Nil =>

        assertEquals(first._1, 10)
        assertEquals(second._1, 20)

        assertEquals(
          Benchmark.countTimesRC(first._2, 0),
          9
        )

        assertEquals(
          Benchmark.countTimesRC(second._2, 0),
          9
        )

      case _ =>
        fail("Two results were expected")
    }
  }


  test("collect QuickSort results") {

    val results =
      ResultCollector.collectQuickSortRC(
        List(10),
        Nil
      )

    results match {

      case result :: Nil =>

        assertEquals(result._1, 10)

        assertEquals(
          Benchmark.countTimesRC(result._2, 0),
          9
        )

      case _ =>
        fail("One result was expected")
    }
  }


  test("collect Closest Points results") {

    val results =
      ResultCollector.collectClosestPointsRC(
        List(10),
        Nil
      )

    results match {

      case result :: Nil =>

        assertEquals(result._1, 10)

        assertEquals(
          Benchmark.countTimesRC(result._2, 0),
          9
        )

      case _ =>
        fail("One result was expected")
    }
  }
}