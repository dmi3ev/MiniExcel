package models

sealed trait Operation {
  val sign: Char
}

case object Sum extends Operation {
  val sign = '+'
}

case object Sub extends Operation {
  val sign = '-'
}

case object Prod extends Operation {
  val sign = '*'
}

case object Div extends Operation {
  val sign = '\\'
}
