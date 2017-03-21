package models

trait Calculated {
  def +(y: CellExpression): CellExpression

  def -(y: CellExpression): CellExpression

  def *(y: CellExpression): CellExpression

  def /(y: CellExpression): CellExpression
}
