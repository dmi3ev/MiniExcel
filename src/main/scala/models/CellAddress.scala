package models

trait CellAddress {
  def row: Int

  def col: Int

  override def toString = s"R${row}C$col"
}
