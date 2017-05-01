package app

import impls.func.FuncMiniExcel

object AppMiniExcel extends App {
  try {
    val input = LoaderInputData.load(Console.in.lines().iterator())
    FuncMiniExcel(input).calc()
  } catch {
    case e: Throwable =>
      import Console.{ RED, RESET }
      println(s"$RESET${RED}Error:$RESET ${e.getMessage}")
  }
}
