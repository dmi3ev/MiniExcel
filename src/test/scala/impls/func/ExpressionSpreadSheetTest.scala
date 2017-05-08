package impls.func

import models.Spreadsheet._
import org.scalatest.FunSuite

class ExpressionSpreadSheetTest extends FunSuite {

  private val row1: Row[models.CellExpression] = Iterable(
    CellExpression(CellUserValue("1")),
    CellExpression(CellUserValue("=A1+1")),
    CellExpression(CellUserValue("=B1+1")),
    CellExpression(CellUserValue("=B1+C1"))
  )

  private val row2: Row[models.CellExpression] = Iterable(
    CellExpression(CellUserValue("=B1*C1+3*D1")),
    CellExpression(CellUserValue("2")),
    CellExpression(CellUserValue("=C2")),
    CellExpression(CellUserValue("=A2+C1/5"))
  )

  private val cells: Rows[models.CellExpression] = Iterable(row1, row2)

  private val spreadsheet = ExpressionSpreadsheet(cells)

  test("find cell A1") {
    assertResult(PositiveInteger(1)) {
      spreadsheet findCell A1StyleAddress("A1")
    }
  }

  test("find cell B2") {
    assertResult(PositiveInteger(2)) {
      spreadsheet findCell A1StyleAddress("B2")
    }
  }

  test("find cell C3") {
    assertResult(ReferenceError) {
      spreadsheet findCell A1StyleAddress("C3")
    }
  }

  test("result in cell B1") {
    assertResult(IntResult(2)) {
      val exp = spreadsheet findCell A1StyleAddress("B1")
      exp getCellResultValue spreadsheet.findCell
    }
  }

  test("result in cell C1") {
    assertResult(IntResult(3)) {
      val exp = spreadsheet findCell A1StyleAddress("C1")
      exp getCellResultValue spreadsheet.findCell
    }
  }

  test("result in cell D1") {
    assertResult(IntResult(5)) {
      val exp = spreadsheet findCell A1StyleAddress("D1")
      exp getCellResultValue spreadsheet.findCell
    }
  }

  test("result in cell A2") {
    assertResult(IntResult(45)) {
      val exp = spreadsheet findCell A1StyleAddress("A2")
      exp getCellResultValue spreadsheet.findCell
    }
  }

  test("find cell D4") {
    assertResult(ReferenceError) {
      spreadsheet findCell A1StyleAddress("D4")
    }
  }

  test("result in cell D2") {
    assertResult(IntResult(9)) {
      val exp = spreadsheet findCell A1StyleAddress("D2")
      exp getCellResultValue spreadsheet.findCell
    }
  }

  test("find cell C2") {
    assertResult(ErrorResult(CircularReferenceError.message)) {
      val exp = spreadsheet findCell A1StyleAddress("C2")
      exp getCellResultValue spreadsheet.findCell
    }
  }
}
