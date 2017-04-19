package models

trait Spreadsheet[T <: CellContent] {
  val height: Int
  val width: Int
  val cells: Spreadsheet.Rows[T]

  def printCells(): Unit = {
    cells foreach { row =>
      row foreach { cell =>
        print(s"""$cell\t""")
      }
      println
    }
  }
}

object Spreadsheet {
  type Row[T <: CellContent] = Iterable[T]
  type Rows[T <: CellContent] = Iterable[Row[T]]
  type findCellFunction = CellAddress => CellExpression
}

trait FindCellFunction {
  def findCell(address: CellAddress): CellExpression
}
