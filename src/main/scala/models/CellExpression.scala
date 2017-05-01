package models

trait CellExpression extends CellContent {
  def getCalculatedValue(findFunc: Spreadsheet.findCellFunction): CalculatedValue

  def getCellResultValue(findFunc: Spreadsheet.findCellFunction): CellResultValue
}
