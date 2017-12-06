package de.hdc.kotlin.measure

import com.danneu.result.Result

/*
 * Combines a value with a physical unit.
 */
data class Measure<T : SIUnit> (val value: Double, val unit: MeasureUnit<T>): Number(), Comparable<Measure<T>> {

  constructor (value: Double, prefix: Prefix, unit: MeasureUnit<T>)
    : this(value, MeasureUnit(prefix, unit.unit))

  companion object {
    val LOG by logger()
    val ZERO = Measure(0.0, UNITLESS)
    val ONE = Measure(1.0, UNITLESS)
    val MIN_VALUE = Measure(Double.MIN_VALUE, UNITLESS)
    val MAX_VALUE = Measure(Double.MAX_VALUE, UNITLESS)
    val POSITIVE_INFINITY = Measure(Double.POSITIVE_INFINITY, MeasureUnit(Prefix.NONE
        , DerivedUnit("POSITIVE_INFINITY", "POSITIVE_INFINITY", SI_COMBINED(Quantity()))))
    val NEGATIVE_INFINITY = Measure(Double.NEGATIVE_INFINITY, MeasureUnit(Prefix.NONE
        , DerivedUnit("NEGATIVE_INFINITY", "NEGATIVE_INFINITY", SI_COMBINED(Quantity()))))
    val NaN = Measure(Double.NaN, MeasureUnit(Prefix.NONE
        , DerivedUnit("NaN", "NaN", SI_COMBINED(Quantity()))))
  }

  private fun testInvalid(d: Double): Boolean {
    return ((d == Double.POSITIVE_INFINITY)
            || (d == Double.NEGATIVE_INFINITY)
            || (d == Double.MAX_VALUE)
            || (d == Double.MIN_VALUE))
  }

  fun getQuantity(): Quantity {
    return unit.unit.baseUnit.quantity
  }

  fun <U : T> convertTo(measureUnit: MeasureUnit<U>): Measure<U> {
    if (measureUnit.equals(unit)) {
      return Measure(value, measureUnit)
    }
    if (value.equals(Double.NaN)) {
      LOG.warning("Division input is not a number! ${toString()} to $measureUnit")
      throw NumberFormatException()
    }
    val r: Double = toDouble().div(measureUnit.prefix.multiplier).div(measureUnit.unit.multiplier)
      when (r) {
          Double.POSITIVE_INFINITY -> {
              LOG.warning("Division overflow! PI ${toString()} to $measureUnit")
              throw NumberFormatException()
          }
          Double.NEGATIVE_INFINITY -> {
              LOG.warning("Division overflow! NI ${toString()} to $measureUnit")
              throw NumberFormatException()
          }
          Double.NaN -> {
              LOG.warning("Division result is not a number! ${toString()} to $measureUnit")
              throw NumberFormatException()
          }
          else -> return Measure(r, measureUnit)
      }
  }

  fun reciprocal(): Measure<T> {
    if (testInvalid(value)) {
      LOG.severe("Division overflow! 1/${toString()}")
      throw NumberFormatException()
    }

    val r: Double = 1.0.div(value)

    if (testInvalid(r)) {
      LOG.severe("Division overflow! 1/${toString()}")
      throw NumberFormatException()
    }
    return Measure(r, unit)
  }

  fun inverse(): Measure<T> {
    if (testInvalid(value)) {
      LOG.severe("Multiplication overflow! -1*${toString()}")
      throw NumberFormatException()
    }

    val r: Double = value.times(-1.0)

    if (testInvalid(r)) {
      LOG.severe("Multiplication overflow! -1*${toString()}")
      throw NumberFormatException()
    }

    return Measure(r, unit)
  }

  fun <U : T> plus(measure: Measure<U>): Measure<T> {
    if (measure.unit.equals(unit)) {
      val m: Double = value.plus(measure.value)
      return Measure(m, unit)
    }
    if (testInvalid(value) || testInvalid(measure.value)) {
      LOG.severe("Addition overflow! ${toString()} + $measure")
      throw NumberFormatException()
    }

    val r: Double = value.plus(measure.toDouble().div(unit.prefix.multiplier))

    if (testInvalid(r)) {
      LOG.severe("Addition overflow! ${toString()} + $measure")
      throw NumberFormatException()
    }

    return Measure(r, unit)
  }

  fun <U : T> minus(measure: Measure<U>): Measure<T> {
    if (measure.unit.equals(unit)) {
      val m: Double = value.minus(measure.value)
      return Measure(m, unit)
    }
    if (testInvalid(value) || testInvalid(measure.value)) {
      LOG.severe("Subtraction overflow! ${toString()} + $measure")
      throw NumberFormatException()
    }

    val r: Double = value.minus(measure.toDouble().div(unit.prefix.multiplier))


    if (testInvalid(r)) {
      LOG.severe("Subtraction overflow! ${toString()} + $measure")
      throw NumberFormatException()
    }

    return Measure(r, unit)
  }

  fun scalar(scalar: Double): Measure<T> {
    if (testInvalid(value)) {
      LOG.severe("Multiplication overflow! ${toString()} * $scalar")
      throw NumberFormatException()
    }

    val r = value.times(scalar)

    if (testInvalid(r)) {
      LOG.severe("Multiplication overflow! ${toString()} * $scalar")
      throw NumberFormatException()
    }

    return Measure(r, unit)
  }

  fun scalar(measure: Measure<SI_UNITLESS>): Measure<T> {
    if (testInvalid(value) || testInvalid(measure.value)) {
      LOG.severe("Multiplication overflow! ${toString()} + $measure")
      throw NumberFormatException()
    }

    val r = value.times(measure.toDouble())

    if (testInvalid(r)) {
      LOG.severe("Multiplication overflow! ${toString()} * $measure")
      throw NumberFormatException()
    }

    return Measure(r, unit)
  }

  fun <U : SIUnit> times(measure: Measure<U>): Measure<SI_COMBINED> {
    if (testInvalid(value) || testInvalid(measure.value)) {
      LOG.severe("Multiplication overflow! ${toString()} + $measure")
      throw NumberFormatException()
    }

    val r = toDouble().times(measure.toDouble())

    if (testInvalid(r)) {
      LOG.warning("Multiplication overflow! ${toString()}/$measure")
      return POSITIVE_INFINITY
    }

    val mu = COMBINED(getQuantity().times(measure.getQuantity()))
    return Measure(r, mu)
  }

  fun <U : SIUnit> div(measure: Measure<U>): Measure<SI_COMBINED> {
    if (measure.value == 0.0) {
      LOG.warning("Division by zero! ${toString()}/$measure")
      return NaN
    }
    if (testInvalid(value) || testInvalid(measure.value)) {
      LOG.severe("Subtraction overflow! ${toString()} + $measure")
      throw NumberFormatException()
    }

    val r = toDouble().div(measure.toDouble())

    if (testInvalid(r)) {
      LOG.warning("Division overflow! ${toString()}/$measure")
      return POSITIVE_INFINITY
    }

    val mu = COMBINED(getQuantity().div(measure.getQuantity()))
    return Measure(r, mu)
  }

  override fun toString(): String {
    return value.toString() + if (unit.toString() == "") "" else " " + unit.toString()
  }

  override fun compareTo(other: Measure<T>): Int {
    return toDouble().compareTo(other.toDouble())
  }

  override fun toByte(): Byte {
    return toDouble().toByte()
  }

  override fun toChar(): Char {
    return toDouble().toChar()
  }

  override fun toDouble(): Double {
      when (value) {
          Double.POSITIVE_INFINITY -> {
              LOG.warning("POSITIVE_INFINITY! ${toString()}")
              return Double.POSITIVE_INFINITY
          }
          Double.NEGATIVE_INFINITY -> {
              LOG.warning("NEGATIVE_INFINITY! ${toString()}")
              return Double.NEGATIVE_INFINITY
          }
          Double.NaN -> {
              LOG.warning("NaN! ${toString()}")
              return Double.NaN
          }
          Double.MAX_VALUE -> return Double.MAX_VALUE
          Double.MIN_VALUE -> return Double.MIN_VALUE
          Double.NaN -> return Double.NaN
          else -> return value.times(unit.prefix.multiplier).times(unit.unit.multiplier)
      }
  }

  override fun toFloat(): Float {
    return toDouble().toFloat()
  }

  override fun toInt(): Int {
    return toDouble().toInt()
  }

  override fun toLong(): Long {
    return toDouble().toLong()
  }

  override fun toShort(): Short {
    return toDouble().toShort()
  }
}

fun doubleFrom(value: String): Result<*, *> {
  //TODO
  return Result.ok(value)
}

fun measureFrom(value: String): Result<*, *> {
  //TODO
//    var v = value
    // numeric value
//    if (v.indexOf(" ") < 0) {
//        return Measure(Double.valueOf(v), UNITLESS)
//    }
    // Unitless
    // Prefix
    // Unit
  return Result.ok(value)
}
