package impls.func

import impls.func.parsers.CellUserValueRegexParser
import models.Spreadsheet.findCellFunction
import models.{ CalculatedValue, CellError, Operation }

object CellExpression {
  def apply(userValue: models.CellUserValue): models.CellExpression = CellUserValueRegexParser.convert(userValue)
}

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
    case CellReference(address) => findFunc(address).getCellResultValue(findFunc)
    case e: Expression => e.calc(findFunc).toCellExpression.getCellResultValue(findFunc)
  }

  override def getCalculatedValue(findFunc: findCellFunction): models.CalculatedValue = this match {
    case i: PositiveInteger => i
    case CellReference(address) => findFunc(address).getCalculatedValue(findFunc)
    case e: Expression => e.calc(findFunc)
    case _ => NotCalculatedValue
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

case class PositiveInteger(int: Int) extends CellExpression with CalculatedValue {

  def +(y: models.CalculatedValue): models.CalculatedValue = y match {
    case PositiveInteger(value) => PositiveInteger(int + value)
    case _ => NotCalculatedValue
  }

  def -(y: models.CalculatedValue): models.CalculatedValue = y match {
    case PositiveInteger(value) => PositiveInteger(int - value)
    case _ => NotCalculatedValue
  }

  def *(y: models.CalculatedValue): models.CalculatedValue = y match {
    case PositiveInteger(value) => PositiveInteger(int * value)
    case _ => NotCalculatedValue
  }

  def /(y: models.CalculatedValue): models.CalculatedValue = y match {
    case PositiveInteger(value) => PositiveInteger(int / value)
    case _ => NotCalculatedValue
  }

  override def toCellExpression: models.CellExpression = this
}

case class Text(text: String) extends CellExpression

case class CellReference(address: models.CellAddress) extends CellExpression

case class Expression(operation: Operation, left: CellExpression, right: CellExpression) extends CellExpression {
  def calc(findFunc: findCellFunction): CalculatedValue =
    operation.calc(left.getCalculatedValue(findFunc), right.getCalculatedValue(findFunc))
}
