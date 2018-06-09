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
    throw IllegalArgumentException("Illegal argument! ${toString()} + $measure")
  }

  val r: Double = this / measure.value

  if (r.testInvalid()) {
    throw ArithmeticException("Division overflow! ${toString()}/$measure")
  }

  return Measure(r, measure.unit)
}

operator fun <T : BaseUnit> Double.times(measure: Measure<T>): Measure<T> {
  if (this.testInvalid() || measure.value.testInvalid()) {
    throw IllegalArgumentException("Illegal argument! ${toString()} + $measure")
  }

  val r: Double = this * measure.value

  if (r.testInvalid()) {
    throw ArithmeticException("Division overflow! ${toString()}/$measure")
  }

  return Measure(r, measure.unit)
}

