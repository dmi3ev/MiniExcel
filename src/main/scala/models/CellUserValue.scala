package models

trait CellUserValue extends CellContent {
  val value: String

  override def toString: String = value
}
