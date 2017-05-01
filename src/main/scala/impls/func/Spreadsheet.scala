package impls.func

import models.Spreadsheet.Rows
import models.{ CellContent, FindCellFunction }

abstract class FuncSpreadsheet[T <: CellContent](cells: Rows[T]) extends models.Spreadsheet[T] {
  val height: Int = cells.size
  require(height > 0, "Spreadsheet height must be greater than zero")

  val width: Int = cells.head.size
  require(width > 0, "Spreadsheet width must be greater than zero")
}

case class ValueSpreadsheet(cells: Rows[models.CellUserValue])
  extends FuncSpreadsheet[models.CellUserValue](cells)

case class ExpressionSpreadsheet(cells: Rows[models.CellExpression])
  extends FuncSpreadsheet[models.CellExpression](cells) with FindCellFunction {

  import scala.util.Try

  def findCell(address: models.CellAddress): models.CellExpression = Try {
    cells.drop(address.row).head
      .drop(address.col).head
  } getOrElse ReferenceError
}

case class ResultSpreadsheet(cells: Rows[models.CellResultValue])
  extends FuncSpreadsheet[models.CellResultValue](cells)
