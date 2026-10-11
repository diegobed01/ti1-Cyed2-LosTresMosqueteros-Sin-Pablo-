package Experiment

class DataLoaderTest extends munit.FunSuite {

  test("parse integer lines") {

    assertEquals(
      DataLoader.parseIntegers(
        List("5", "3", "8", "1", "2")
      ),
      List(5, 3, 8, 1, 2)
    )
  }


  test("parse empty list") {

    assertEquals(
      DataLoader.parseIntegers(Nil),
      Nil
    )
  }


  test("load integers from thefile") {

    assertEquals(
      DataLoader.loadIntegers("data/sample-integers.txt"),
      List(5, 3, 8, 1, 2)
    )
  }
}