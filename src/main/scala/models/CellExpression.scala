package models

trait CellExpression extends CellContent {
  val refs: Set[CellAddress]

  def getCalculatedValue(findFunc: Spreadsheet.findCellFunction): CalculatedValue

  def getCellResultValue(findFunc: Spreadsheet.findCellFunction): CellResultValue
}
