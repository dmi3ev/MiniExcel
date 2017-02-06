package models

trait CellContentParser {
  def convert(in: models.CellValue): models.CellExpression
}
