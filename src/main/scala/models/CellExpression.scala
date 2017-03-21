package models

trait CellExpression extends CellContent {
  def getCellResultValue(findFunc: Spreadsheet.findCellFunction): CellResultValue
}
