package app

import impls.func.FuncMiniExcel

import scala.io.Source

object AppMiniExcel extends App {
  val input = LoaderInputData.load(Source.fromResource("test2"))
  FuncMiniExcel(input).calc()
}
