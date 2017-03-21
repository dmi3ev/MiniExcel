package models.parsers

trait CellValueToCellExpression {
  def convert(in: models.CellValue): models.CellExpression
}
