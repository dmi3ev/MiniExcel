package app

import impls.func.parsers.{CellValueRegexParser, SourceToFuncCellValues}
import impls.func.{ExpressionSpreadsheet, ResultSpreadsheet, ValueSpreadsheet}
import models._

case class FuncMiniExcel(input: Iterable[Iterable[String]]) extends MiniExcel(input) {

  override def createInputSpreadsheet = {
    val cells: Spreadsheet.Rows[CellValue] = input map { row =>
      row map SourceToFuncCellValues.convert
    }
    ValueSpreadsheet(cells)
  }

  override def createExpressionSpreadsheet(spreadsheet: Spreadsheet[CellValue]) = {
    val cells = spreadsheet.cells map { row =>
      row map CellValueRegexParser.convert
    }
    ExpressionSpreadsheet(cells)
  }

  override def createOutputSpreadsheet(spreadsheet: Spreadsheet[CellExpression] with FindCellFunction) = {
    val cells: Spreadsheet.Rows[CellResultValue] = spreadsheet.cells map { row =>
      row map { exp =>
        exp.getCellResultValue(spreadsheet.findCell _)
      }
    }
    ResultSpreadsheet(cells)

  }
}

