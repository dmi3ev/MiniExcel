package impls.func

import models.CellError

sealed trait ResultValue extends models.CellResultValue {
  override def toString: String = this match {
    case NothingResult => ""
    case e: CellError => e.getMessage
    case t: TextResult => t.text
    case i: IntResult => i.int.toString
  }
}

case object NothingResult extends ResultValue

case class ErrorResult(message: String) extends ResultValue with CellError

case class TextResult(text: String) extends ResultValue

case class IntResult(int: Int) extends ResultValue
