package impls.func

import impls.func.parsers.{CellValueRegexParser, SourceToFuncCellValues}
import models.MiniExcel
import models.MiniExcel._

case class FuncMiniExcel(input: Input) extends MiniExcel(input) {

  override def createInputSpreadsheet: ValueSpreadsheet = {
    val cells = input map { row =>
      row map SourceToFuncCellValues.convert
    }
    ValueSpreadsheet(cells)
  }

  override def createExpressionSpreadsheet(spreadsheet: InputSpreadsheet): ExpSpreadsheet = {
    val cells = spreadsheet.cells map { row =>
      row map CellValueRegexParser.convert
    }
    ExpressionSpreadsheet(cells)
  }

  override def createOutputSpreadsheet(spreadsheet: ExpSpreadsheet): OutputSpreadsheet = {
    val cells = spreadsheet.cells map { row =>
      row map { exp =>
        exp.getCellResultValue(spreadsheet.findCell)
      }
    }
    ResultSpreadsheet(cells)
  }
}
