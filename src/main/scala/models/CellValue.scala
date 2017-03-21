package models

trait CellValue extends CellContent {
  val value: String

  override def toString: String = value
}
