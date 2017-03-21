package app

import models._

abstract class MiniExcel(input: Iterable[Iterable[String]]) {
  def createInputSpreadsheet: Spreadsheet[CellValue]

  def createExpressionSpreadsheet(spreadsheet: Spreadsheet[CellValue]): Spreadsheet[CellExpression] with FindCellFunction

  def createOutputSpreadsheet(spreadsheet: Spreadsheet[CellExpression] with FindCellFunction): Spreadsheet[CellResultValue]

  final def calc = {
    val inputSpreadsheet = createInputSpreadsheet
    val spreadsheet = createExpressionSpreadsheet(inputSpreadsheet)
    val outputSpreadsheet = createOutputSpreadsheet(spreadsheet)
    outputSpreadsheet.cells map { row =>
      row map print
      println
    }
  }
}
