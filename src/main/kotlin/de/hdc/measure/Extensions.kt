package de.hdc.measure

fun Double.format(digits: Int): String = java.lang.String.format("%.${digits}f", this)

fun Double.testInvalid(): Boolean {
  return ((this == Double.POSITIVE_INFINITY)
          || (this == Double.NEGATIVE_INFINITY)
          || (this == Double.MAX_VALUE)
          || (this == Double.MIN_VALUE))
}

operator fun <T : BaseUnit> Double.div(measure: Measure<T>): Measure<T> {
  if (measure.value == 0.0) {
    throw ArithmeticException("Division by zero! ${toString()}/$measure")
  }
  if (this.testInvalid() || measure.value.testInvalid()) {
    throw NumberFormatException("Division overflow! ${toString()} + $measure")
  }

  val r: Double = this / measure.toDouble()

  if (r.testInvalid()) {
    throw ArithmeticException("Division overflow! ${toString()}/$measure")
  }

  return Measure(r, measure.unit)
}

