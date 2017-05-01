package models

trait NotCalculatedValue extends CalculatedValue {
  override def +(y: CalculatedValue): CalculatedValue = this

  override def -(y: CalculatedValue): CalculatedValue = this

  override def *(y: CalculatedValue): CalculatedValue = this

  override def /(y: CalculatedValue): CalculatedValue = this
}
