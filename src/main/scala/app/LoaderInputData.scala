package app

import models.MiniExcel

import scala.io.Source

object LoaderInputData {
  val separator = "\\t"

  def load(source: Source): MiniExcel.Input = {
    val iterator = source.getLines
    val firstLine = iterator.next

    val (height, width) = firstLine split separator map (_.toInt) match {
      case Array(h, w) => h -> w
      case _ => throw new Error("Not correct data in first line")
    }

    (1 to height) map { i =>
      val row = iterator.next split separator
      assert(row.length == width, s"Not correct the spreadsheet data in line $i")
      row.toIterable
    }

  }
}
