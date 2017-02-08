package impls.func.parsers

import impls.func._
import models.CellContentParser

import scala.util.Try

class CellContentRegexParser extends CellContentParser {

  import scala.util.parsing.combinator._

  private class Parser extends RegexParsers {

    def nonNegativeNumber: Parser[PositiveInteger] = """\d+""".r ^^ { n => PositiveInteger(n.toInt) }

    // TODO некоторые адреса ячеек недопустимые, например, A0
    def cellReference: Parser[CellReference] =
      """[A-Za-z][0-9]""".r ^^ { r => CellReference(A1StyleAddress(r.toUpperCase)) }

    val operationSign = """[+-\\*/]""".r

    def term: Parser[FuncCellExpression] = cellReference | nonNegativeNumber

    def operation: Parser[models.Operation] = operationSign ^^ {
      case "+" => models.Sum
      case "-" => models.Sub
      case "*" => models.Prod
      case "/" => models.Div
    }

    // TODO "printable character"
    def text = "'" ~> ".*".r ^^ { t => Text(t) }

    def expression: Parser[FuncCellExpression] = term ~ rep(operation ~ term) ^^ {
      case term ~ Nil => term
      case left ~ list => list.foldLeft(left) { case (left, operation ~ right) => Expression(operation, left, right) }
    }

    def cellValue: Parser[FuncCellExpression] = nonNegativeNumber | text | "=" ~> expression
  }

  private val parser = new Parser

  override def convert(in: models.CellValue): models.CellExpression = {
    if (in.value == "") {
      Nothing
    } else {
      val result = Try(parser.parseAll(parser.cellValue, in.value).get)
      result.getOrElse(InputError)
    }
  }
}
