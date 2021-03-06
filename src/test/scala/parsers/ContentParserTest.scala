package parsers

import impls.func._
import impls.func.parsers.CellUserValueRegexParser
import org.scalatest.FunSuite

class ContentParserTest extends FunSuite {
  val parser = CellUserValueRegexParser

  test("Empty cell value") {
    assert(parser.convert(CellUserValue("")) == Nothing)
  }

  test("Non negative number") {
    assert(parser.convert(CellUserValue("12")) == PositiveInteger(12))
  }

  test("Negative number") {
    assert(parser.convert(CellUserValue("-12")) == InputError)
  }

  test("Text") {
    assert(parser.convert(CellUserValue("'Sample")) == Text("Sample"))
  }

  test("=C2") {
    assert(parser.convert(CellUserValue("=C2")) == CellReference(A1StyleAddress("C2")))
  }

  test("=c2") {
    assert(parser.convert(CellUserValue("=c2")) == CellReference(A1StyleAddress("C2")))
  }

  test("=c23") {
    assert(parser.convert(CellUserValue("=c23")) == InputError)
  }

  test("=c0") {
    assert(parser.convert(CellUserValue("=c0")) == InputError)
  }

  test("=ac2") {
    assert(parser.convert(CellUserValue("=ac2")) == InputError)
  }

  test("c2") {
    assert(parser.convert(CellUserValue("c2")) == InputError)
  }

  test("c-2") {
    assert(parser.convert(CellUserValue("c-2")) == InputError)
  }

  test("=2") {
    assert(parser.convert(CellUserValue("=2")) == PositiveInteger(2))
  }

  test("=-2") {
    assert(parser.convert(CellUserValue("=-2")) == InputError)
  }

  test("=4-3") {
    assert(parser.convert(CellUserValue("=4-3")) == Expression(models.Sub, PositiveInteger(4), PositiveInteger(3)))
  }

  test("=4*3") {
    assert(parser.convert(CellUserValue("=4*3")) == Expression(models.Prod, PositiveInteger(4), PositiveInteger(3)))
  }

  test("=4+3") {
    assert(parser.convert(CellUserValue("=4+3")) == Expression(models.Sum, PositiveInteger(4), PositiveInteger(3)))
  }

  test("=4/2") {
    assert(parser.convert(CellUserValue("=4/2")) == Expression(models.Div, PositiveInteger(4), PositiveInteger(2)))
  }

  test("=4-2*5") {
    assert(parser.convert(CellUserValue("=4-2*5")) == Expression(models.Prod,
      Expression(models.Sub, PositiveInteger(4), PositiveInteger(2)), PositiveInteger(5)))
  }

  test("=A2*B1") {
    assert(parser.convert(CellUserValue("=A2*B1")) == Expression(models.Prod, CellReference(A1StyleAddress("A2")),
      CellReference(A1StyleAddress("B1"))))
  }

  test("=A1+B1*C1/5") {
    assert {
      parser.convert(CellUserValue("=A1+B1*C1/5")) ==
        Expression(models.Div,
          Expression(models.Prod,
            Expression(models.Sum,
              CellReference(A1StyleAddress("A1")), CellReference(A1StyleAddress("B1"))),
            CellReference(A1StyleAddress("C1"))),
          PositiveInteger(5))
    }
  }

}
