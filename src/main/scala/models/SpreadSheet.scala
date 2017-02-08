package models

trait Spreadsheet {
  val height: Int
  val width: Int

  def getCell(address: CellAddress): CellContent

  def print

  require(height > 0, "Spreadsheet height must be greater than zero")
  require(width > 0, "Spreadsheet width must be greater than zero")
}
