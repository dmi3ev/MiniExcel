package impls.func

import models.Spreadsheet.findCellFunction
import models.{Calculated, CellError, Operation}

sealed trait CellExpression extends models.CellExpression {
  override def toString: String = this match {
    case Nothing => ""
    case error: CellError => error.getMessage
    case PositiveInteger(int) => int.toString
    case Text(text) => text
    case CellReference(address) => address.toString
    case Expression(op, lt, rt) => s"${lt.toString} ${op.sign} ${rt.toString}"
  }

  override def getCellResultValue(findFunc: findCellFunction): models.CellResultValue = this match {
    case Nothing => NothingResult
    case error: CellError => ErrorResult(error.message)
    case PositiveInteger(int) => IntResult(int)
    case Text(text) => TextResult(text)
    case r: CellReference => r.find(findFunc)
    case e: Expression => e.calc(findFunc).getCellResultValue(findFunc)
  }
}

case object Nothing extends CellExpression

case object InputError extends CellExpression with CellError {
  val message = "Input Error"
}

case object ReferenceError extends CellExpression with CellError {
  val message = "Reference Error"
}

case object CalculatedError extends CellExpression with CellError {
  val message = "Calculated Error"
}

case class PositiveInteger(int: Int) extends CellExpression with Calculated {
  require(int >= 0)

  def +(y: models.CellExpression): models.CellExpression = y match {
    case PositiveInteger(value) => PositiveInteger(int + value)
    case _ => CalculatedError
  }

  def -(y: models.CellExpression): models.CellExpression = y match {
    case PositiveInteger(value) => PositiveInteger(int - value)
    case _ => CalculatedError
  }

  def *(y: models.CellExpression): models.CellExpression = y match {
    case PositiveInteger(value) => PositiveInteger(int * value)
    case _ => CalculatedError
  }

  def /(y: models.CellExpression): models.CellExpression = y match {
    case PositiveInteger(value) => PositiveInteger(int / value)
    case _ => CalculatedError
  }
}

case class Text(text: String) extends CellExpression

case class CellReference(address: models.CellAddress) extends CellExpression {
  def find(findFunc: findCellFunction) = {
    findFunc(address).getCellResultValue(findFunc)
  }
}

case class Expression(operation: Operation, leftTerm: CellExpression, rightTerm: CellExpression)
  extends CellExpression {
  def calc(findFunc: findCellFunction): models.CellExpression = this match {
    case Expression(op, left: Calculated, right: Expression) =>
      op.calc(left, right.calc(findFunc))
    case Expression(op, x: Calculated, y) => op.calc(x, y)
    case _ => CalculatedError
  }
}
