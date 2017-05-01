package impls.func

object NotCalculatedValue extends models.NotCalculatedValue {
  override def toCellExpression: models.CellExpression = CalculatedError
}
