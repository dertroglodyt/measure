package de.hdc.measure

import java.util.*
import kotlin.math.roundToInt

/**
 * Ignores last to decimal digits when comparing for equal.
 */
infix fun Double.aproximates(x: Double): Boolean  {
  return (this == x) || ((x < this + 1e-14) && (x > this - 1e-14))
}

fun Double.format(digits: Int = 3): String {
  val invalid = this.testInvalid()
  return when {
    (invalid) -> this.toString()
    (digits < 0) -> throw IllegalArgumentException("Digit count can not be negativ! <$digits>")
    else -> {
      var s = java.lang.String.format(Locale.GERMANY, "%.${digits}f", this)

      // insert thousand spacer
      val x = s.indexOf(',')
      var t = ""

      // insert spacer behind decimal point
      if (x >= 0) {
        var u = s.substring(x + 1)
        t = ","
        s = s.substring(0, x)

        while (u.length > 3) {
          t = t + u.substring(0..2) + "\u202F"
          u = u.substring(3)
        }
        t += u
      }

      val log = Math.log10(Math.abs(this)).roundToInt() / 3
      if (log > 0) {
        // insert spacer in front of decimal point
        for (i in 1..log) {
          t = "\u202F" + s.substring(s.lastIndex - 2) + t
          s = s.substring(0, s.lastIndex - 2)
        }
      }
      s + t
    }
  }
}

fun Double.testInvalid(): Boolean {
  // "euqals" not replaceable by "=="
  return ((this.equals(Double.POSITIVE_INFINITY))
          || (this.equals(Double.NEGATIVE_INFINITY))
          || (this.equals(Double.MAX_VALUE))
          || (this.equals(Double.MIN_VALUE))
          || (this.equals(Double.NaN)))
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

