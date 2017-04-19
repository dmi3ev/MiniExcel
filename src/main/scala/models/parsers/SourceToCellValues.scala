package models.parsers

trait SourceToCellValues {
  def convert(source: String): models.CellUserValue
}
