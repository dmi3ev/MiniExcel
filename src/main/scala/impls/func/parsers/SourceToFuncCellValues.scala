package impls.func.parsers

import impls.func.CellUserValue

object SourceToFuncCellValues extends models.parsers.SourceToCellValues {

  override def convert(source: String): models.CellUserValue = {
    CellUserValue(source)
  }
}
