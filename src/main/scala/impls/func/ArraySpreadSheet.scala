package impls.func

case class ArraySpreadsheet(height: Int, width: Int) extends models.Spreadsheet {
  val spreadsheet = new Array[Array[models.Cell]](height)(width)
}
