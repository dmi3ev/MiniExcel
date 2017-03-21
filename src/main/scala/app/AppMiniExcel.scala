package app

import scala.io.Source

object AppMiniExcel extends App {
  val input = LoaderInputData.load(Source.fromResource("test1"))
  FuncMiniExcel(input).calc
}
