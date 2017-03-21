package impls.func

import models.CellError

sealed trait ResultValue extends models.CellResultValue

case object NothingResult extends ResultValue

case object CircleReference extends ResultValue with CellError {
  override val message: String = "Circle Reference"
}

case class ErrorResult(message: String) extends ResultValue with CellError

case class TextResult(text: String) extends ResultValue

case class IntResult(int: Int) extends ResultValue