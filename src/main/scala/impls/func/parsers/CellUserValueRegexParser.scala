package impls.func.parsers

import impls.func._

import scala.util.Try
import scala.util.matching.Regex

object CellUserValueRegexParser {

  import scala.util.parsing.combinator._

  private class Parser extends RegexParsers {

    def nonNegativeNumber: Parser[PositiveInteger] = """\d+""".r ^^ { n => PositiveInteger(n.toInt) }

    def cellReference: Parser[CellReference] =
      """[A-Za-z][1-9]""".r ^^ { r => CellReference(A1StyleAddress(r.toUpperCase)) }

    val operationSign: Regex = """[+-\\*/]""".r

    def term: Parser[CellExpression] = cellReference | nonNegativeNumber

    def operation: Parser[models.Operation] = operationSign ^^ {
      case "+" => models.Sum
      case "-" => models.Sub
      case "*" => models.Prod
      case "/" => models.Div
    }

    def text: Parser[Text] = "'" ~> ".*".r ^^ { t => Text(t) }

    def expression: Parser[CellExpression] = term ~ rep(operation ~ term) ^^ {
      case term ~ Nil => term
      case expr ~ list => list.foldLeft(expr) { case (left, operation ~ right) => Expression(operation, left, right) }
    }

    def cellValue: Parser[CellExpression] = nonNegativeNumber | text | "=" ~> expression
  }

  private val parser = new Parser

  def convert(userValue: models.CellUserValue): models.CellExpression = {
    if (userValue.value == "") {
      Nothing
    } else {
      Try {
        parser.parseAll(parser.cellValue, userValue.value).get
      } getOrElse InputError
    }
  }
}
