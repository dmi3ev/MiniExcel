package impls.func

import models.MiniExcel._

case class FuncMiniExcel(input: Input) extends models.MiniExcel(input) {

  override def createInputSpreadsheet(input: Input): InputSpreadsheet = {
    val cells = input map { row =>
      row map CellUserValue
    }
    ValueSpreadsheet(cells)
  }

  override def createExpressionSpreadsheet(spreadsheet: InputSpreadsheet): ExpSpreadsheet = {
    val cells = spreadsheet.cells map { row =>
      row map { userValue =>
        CellExpression(userValue)
      }
    }
    ExpressionSpreadsheet(cells)
  }

  override def createOutputSpreadsheet(spreadsheet: ExpSpreadsheet): OutputSpreadsheet = {
    val cells = spreadsheet.cells map { row =>
      row map { exp =>
        exp getCellResultValue spreadsheet.findCell
      }
    }
    ResultSpreadsheet(cells)
  }
}
