package impls.func

case class A1StyleAddress(address: String) extends models.CellAddress {
  require(address.length == 2, "Cell address length mast be two chars")

  private val colChar = address(0)
  private val rowChar = address(1)

  require(colChar >= A1StyleAddress.Column.first && colChar <= A1StyleAddress.Column.last, "Column address is invalid")
  require(rowChar >= A1StyleAddress.Row.first && rowChar <= A1StyleAddress.Row.last, "Row address is invalid")

  override def row: Int = rowChar - A1StyleAddress.Row.first.toInt

  override def col: Int = colChar.toInt - A1StyleAddress.Column.first.toInt
}

object A1StyleAddress {

  private object Column {
    val first = 'A'
    val last = 'Z'
  }

  private object Row {
    val first = '1'
    val last = '9'
  }

}
