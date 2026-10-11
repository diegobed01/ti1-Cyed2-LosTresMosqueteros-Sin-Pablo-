package Experiment

class DataGeneratorTest extends munit.FunSuite {

  test("generate ascending list") {

    assertEquals(
      DataGenerator.generateAscending(5),
      List(1, 2, 3, 4, 5)
    )
  }


  test("generate one element") {

    assertEquals(
      DataGenerator.generateAscending(1),
      List(1)
    )
  }


  test("generate descending list") {

    assertEquals(
      DataGenerator.generateDescending(5),
      List(5, 4, 3, 2, 1)
    )
  }


  test("generate repeted values") {

    assertEquals(
      DataGenerator.generateRepeated(5, 2),
      List(2, 2, 2, 2, 2)
    )
  }
}