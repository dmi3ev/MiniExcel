package impls.func.parsers

import impls.func.CellValue

object SourceToFuncCellValues extends models.parsers.SourceToCellValues {

  override def convert(source: String): models.CellValue = {
    CellValue(source)
  }
}
