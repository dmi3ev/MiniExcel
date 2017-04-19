package models

abstract class MiniExcel(input: MiniExcel.Input) {

  import MiniExcel._

  def createInputSpreadsheet: InputSpreadsheet

  def createExpressionSpreadsheet(spreadsheet: InputSpreadsheet): ExpSpreadsheet

  def createOutputSpreadsheet(spreadsheet: ExpSpreadsheet): OutputSpreadsheet

  final def calc(): Unit = {
    val inputSpreadsheet = createInputSpreadsheet
    val spreadsheet = createExpressionSpreadsheet(inputSpreadsheet)
    val outputSpreadsheet = createOutputSpreadsheet(spreadsheet)
    outputSpreadsheet.printCells()
  }
}

object MiniExcel {
  type Input = Iterable[Iterable[String]]
  type InputSpreadsheet = Spreadsheet[CellUserValue]
  type ExpSpreadsheet = Spreadsheet[CellExpression] with FindCellFunction
  type OutputSpreadsheet = Spreadsheet[CellResultValue]
}
