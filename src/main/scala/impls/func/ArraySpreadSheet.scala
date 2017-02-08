package impls.func

import models.{ CellAddress, CellContent }

case class ArraySpreadsheet(height: Int, width: Int) extends models.Spreadsheet {
  val spreadsheet = new Array[Array[models.Cell]](height)(width)

  override def getCell(address: CellAddress): CellContent = ???

  override def print: Unit = ???
}
