package models

trait CellError {
  val message: String

  def getMessage: String = s"#$message"
}
