package models.parsers

trait CellValueToCellExpression {
  def convert(in: models.CellUserValue): models.CellExpression
}
