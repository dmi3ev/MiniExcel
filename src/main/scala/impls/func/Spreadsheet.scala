package impls.func

import models.FindCellFunction
import models.Spreadsheet.Rows

import scala.util.Try

abstract class Spreadsheet[T <: models.CellContent](cells: Rows[T]) extends models.Spreadsheet[T] {
  val height: Int = cells.size
  require(height > 0, "Spreadsheet height must be greater than zero")

  val width: Int = cells.head.size
  require(width > 0, "Spreadsheet width must be greater than zero")


}

case class ValueSpreadsheet(cells: Rows[models.CellValue]) extends Spreadsheet[models.CellValue](cells)

case class ResultSpreadsheet(cells: Rows[models.CellResultValue]) extends Spreadsheet[models.CellResultValue](cells)

case class ExpressionSpreadsheet(cells: Rows[models.CellExpression]) extends Spreadsheet[models.CellExpression](cells) with FindCellFunction {
  def findCell(address: models.CellAddress): models.CellExpression = Try {
    cells.drop(address.row).head
      .drop(address.col).head
  } getOrElse ReferenceError

}