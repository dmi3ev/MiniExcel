package impls.func

import impls.func.errors.FCellError
import models.Operation

sealed trait FuncCellExpression extends models.CellExpression {
  override def getPrintableContent: String = this match {
    case Nothing => ""
    case error: FCellError => error.getMessage
    case PositiveInteger(int) => int.toString
    case Text(text) => s"'$text"
    case CellReference(address) => address.toString
    case Expression(op, lt, rt) => s"${lt.toString} ${op.toString} ${rt.toString}"
  }
}

case object Nothing extends FuncCellExpression

case object InputError extends FuncCellExpression with FCellError {
  val message = "InputError"
}

case class PositiveInteger(int: Int) extends FuncCellExpression {
  require(int >= 0)
}

case class Text(text: String) extends FuncCellExpression

case class CellReference(address: CellAddress) extends FuncCellExpression

case class Expression(operation: Operation, leftTerm: FuncCellExpression, rightTerm: FuncCellExpression) extends FuncCellExpression
