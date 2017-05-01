package models

sealed trait Operation {
  val sign: Char

  def calc(x: CalculatedValue, y: CalculatedValue): CalculatedValue
}

case object Sum extends Operation {
  val sign = '+'

  override def calc(x: CalculatedValue, y: CalculatedValue): CalculatedValue = x + y
}

case object Sub extends Operation {
  val sign = '-'

  override def calc(x: CalculatedValue, y: CalculatedValue): CalculatedValue = x - y
}

case object Prod extends Operation {
  val sign = '*'

  override def calc(x: CalculatedValue, y: CalculatedValue): CalculatedValue = x * y
}

case object Div extends Operation {
  val sign = '/'

  override def calc(x: CalculatedValue, y: CalculatedValue): CalculatedValue = x / y
}
