package models

trait CalculatedValue {
  def toCellExpression: CellExpression

  def +(y: CalculatedValue): CalculatedValue

  def -(y: CalculatedValue): CalculatedValue

  def *(y: CalculatedValue): CalculatedValue

  def /(y: CalculatedValue): CalculatedValue
}
