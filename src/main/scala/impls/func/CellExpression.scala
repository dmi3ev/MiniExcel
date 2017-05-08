package impls.func

import impls.func.parsers.CellUserValueRegexParser
import models.Spreadsheet.findCellFunction
import models.{ CalculatedValue, CellAddress, CellError, Operation }

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
    case reference: CellReference => reference.find(findFunc).getCellResultValue(findFunc)
    case e: Expression => e.calc(findFunc).toCellExpression.getCellResultValue(findFunc)
  }

  override def getCalculatedValue(findFunc: findCellFunction): models.CalculatedValue = this match {
    case i: PositiveInteger => i
    case reference: CellReference => reference.find(findFunc).getCalculatedValue(findFunc)
    case e: Expression => e.calc(findFunc)
    case _ => NotCalculatedValue
  }

  override val refs: Set[CellAddress] = Set.empty
}

case object Nothing extends CellExpression

case object InputError extends CellExpression with CellError {
  val message = "Input Error"
}

case object ReferenceError extends CellExpression with CellError {
  val message = "Reference Error"
}

case object CircularReferenceError extends CellExpression with CellError {
  val message = "Circular Reference"
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

case class CellReference(address: models.CellAddress) extends CellExpression {
  def find(findFunc: findCellFunction): models.CellExpression = {
    if (hasCircularReference(findFunc, refs, Set.empty)) {
      CircularReferenceError
    } else {
      findFunc(address)
    }
  }

  override val refs: Set[CellAddress] = Set(address)

  private def hasCircularReference(findFunc: findCellFunction, testingRefs: Set[CellAddress],
                                   testedRefs: Set[CellAddress]): Boolean = {
    testingRefs.exists { address =>
      findFunc(address).refs match {
        case cellRefs if !cellRefs.exists(testedRefs.contains) =>
          hasCircularReference(findFunc, cellRefs, testedRefs + address)
        case s if s.isEmpty => false
        case _ => true
      }
    }
  }
}

case class Expression(operation: Operation, left: CellExpression, right: CellExpression) extends CellExpression {
  def calc(findFunc: findCellFunction): CalculatedValue =
    operation.calc(left.getCalculatedValue(findFunc), right.getCalculatedValue(findFunc))

  override val refs: Set[CellAddress] = left.refs ++ right.refs
}
