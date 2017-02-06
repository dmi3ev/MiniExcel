package models

trait CellValue extends CellContent {
  val value: String

  override def getPrintableContent: String = value
}
