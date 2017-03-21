package parsers

import impls.func._
import impls.func.parsers.CellValueRegexParser
import org.scalatest.FunSuite

class ContentParserTest extends FunSuite {
  val parser = CellValueRegexParser

  test("Empty cell value") {
    assert(parser.convert(CellValue("")) == Nothing)
  }

  test("Non negative number") {
    assert(parser.convert(CellValue("12")) == PositiveInteger(12))
  }

  test("Negative number") {
    assert(parser.convert(CellValue("-12")) == InputError)
  }

  test("Text") {
    assert(parser.convert(CellValue("'Sample")) == Text("Sample"))
  }

  test("=C2") {
    assert(parser.convert(CellValue("=C2")) == CellReference(A1StyleAddress("C2")))
  }

  test("=c2") {
    assert(parser.convert(CellValue("=c2")) == CellReference(A1StyleAddress("C2")))
  }

  test("=c23") {
    assert(parser.convert(CellValue("=c23")) == InputError)
  }

  test("=c0") {
    assert(parser.convert(CellValue("=c0")) == InputError)
  }

  test("=ac2") {
    assert(parser.convert(CellValue("=ac2")) == InputError)
  }

  test("c2") {
    assert(parser.convert(CellValue("c2")) == InputError)
  }

  test("c-2") {
    assert(parser.convert(CellValue("c-2")) == InputError)
  }

  test("=2") {
    assert(parser.convert(CellValue("=2")) == PositiveInteger(2))
  }

  test("=-2") {
    assert(parser.convert(CellValue("=-2")) == InputError)
  }

  test("=4-3") {
    assert(parser.convert(CellValue("=4-3")) == Expression(models.Sub, PositiveInteger(4), PositiveInteger(3)))
  }

  test("=4*3") {
    assert(parser.convert(CellValue("=4*3")) == Expression(models.Prod, PositiveInteger(4), PositiveInteger(3)))
  }

  test("=4+3") {
    assert(parser.convert(CellValue("=4+3")) == Expression(models.Sum, PositiveInteger(4), PositiveInteger(3)))
  }

  test("=4/2") {
    assert(parser.convert(CellValue("=4/2")) == Expression(models.Div, PositiveInteger(4), PositiveInteger(2)))
  }

  test("=4-2*5") {
    assert(parser.convert(CellValue("=4-2*5")) == Expression(models.Prod,
      Expression(models.Sub, PositiveInteger(4), PositiveInteger(2)), PositiveInteger(5)))
  }

  test("=A2*B1") {
    assert(parser.convert(CellValue("=A2*B1")) == Expression(models.Prod, CellReference(A1StyleAddress("A2")),
      CellReference(A1StyleAddress("B1"))))
  }

  test("=A1+B1*C1/5") {
    assert {
      parser.convert(CellValue("=A1+B1*C1/5")) ==
        Expression(models.Div,
          Expression(models.Prod,
            Expression(models.Sum,
              CellReference(A1StyleAddress("A1")), CellReference(A1StyleAddress("B1"))),
            CellReference(A1StyleAddress("C1"))),
          PositiveInteger(5))
    }
  }

}
