package impls.func.errors

trait FCellError {
  val message: String

  def getMessage: String = s"#$message"
}
