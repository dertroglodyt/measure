package de.hdc.measure

import com.danneu.result.Result

fun measureFrom(value: String): Result<*, *> {
  throw NotImplementedError()
  //TODO
//    var v = value
  // numeric value
//    if (v.indexOf(" ") < 0) {
//        return Measure(Double.valueOf(v), UNITLESS)
//    }
  // Unitless
  // Prefix
  // Unit
//  return Result.ok(value)
}

/*
 * Combines a value with a physical unit.
 */
data class Measure<T : BaseUnit> (val value: Double, val unit: MeasureUnit<T>): Number(), Comparable<Measure<T>> {

  constructor (value: Double, prefix: Prefix, unit: MeasureUnit<T>)
    : this(value, MeasureUnit(prefix, unit.name, unit.symbol, unit.baseUnit, unit.multiplier, unit.increment))

  companion object {
    val ZERO = Measure(0.0, UNITLESS)
    val ONE = Measure(1.0, UNITLESS)
    val MIN_VALUE = Measure(Double.MIN_VALUE, UNITLESS)
    val MAX_VALUE = Measure(Double.MAX_VALUE, UNITLESS)
    val POSITIVE_INFINITY = Measure(Double.POSITIVE_INFINITY, MeasureUnit(Prefix.NONE
            , "POSITIVE_INFINITY", "POSITIVE_INFINITY", SI_COMBINED(Quantity())))
    val NEGATIVE_INFINITY = Measure(Double.NEGATIVE_INFINITY, MeasureUnit(Prefix.NONE
            , "NEGATIVE_INFINITY", "NEGATIVE_INFINITY", SI_COMBINED(Quantity())))
    val NaN = Measure(Double.NaN, MeasureUnit(Prefix.NONE
            , "NaN", "NaN", SI_COMBINED(Quantity())))
  }

  fun getQuantity(): Quantity {
    return unit.baseUnit.quantity
  }

  //todo
  /**
//  fun <U : T> convertTo(measureUnit: MeasureUnit<U>): Measure<U> {
//    if (measureUnit == unit) {
//      return Measure(value, measureUnit)
//    }
//    if (value == Double.NaN) {
//      LOG.warning("Division input is not a number! ${toString()} convertTo $measureUnit")
//      throw NumberFormatException()
//    }
//    val r: Double = toDouble() / measureUnit.prefix.multiplier / measureUnit.unit.multiplier
//      when (r) {
//          Double.POSITIVE_INFINITY -> {
//              LOG.warning("Division overflow! PI ${toString()} convertTo $measureUnit")
//              throw NumberFormatException()
//          }
//          Double.NEGATIVE_INFINITY -> {
//              LOG.warning("Division overflow! NI ${toString()} convertTo $measureUnit")
//              throw NumberFormatException()
//          }
//          Double.NaN -> {
//              LOG.warning("Division result is not a number! ${toString()} convertTo $measureUnit")
//              throw NumberFormatException()
//          }
//          else -> return Measure(r, measureUnit)
//      }
//  }
*/

  override fun equals(other: Any?): Boolean {
    if (other !is Measure<*>) {
      return false
    }
      if (!unit.isEquivalentTo(other.unit)) {
        return false
      }
    return (toDouble().compareTo(other.toDouble()) == 0)
  }

  override fun hashCode(): Int {
    return super.hashCode()
  }

  fun <U : BaseUnit> convertTo(measureUnit: MeasureUnit<U>): Measure<U> {
    if (measureUnit == unit) {
      return Measure(value, measureUnit)
    }
    if (! measureUnit.isEquivalentTo(this.unit)) {
      throw java.lang.IllegalArgumentException("Inconvertible units! ${toString()} convertTo $measureUnit")
    }
    if (value == Double.NaN) {
      throw NumberFormatException("Division input is not a number! ${toString()} convertTo $measureUnit")
    }
    val r = (toDouble() - unit.increment) / measureUnit.prefix.multiplier /
            measureUnit.multiplier + measureUnit.increment
    when (r) {
      Double.POSITIVE_INFINITY -> {
        throw NumberFormatException("Division overflow! PI ${toString()} convertTo $measureUnit")
      }
      Double.NEGATIVE_INFINITY -> {
        throw NumberFormatException("Division overflow! NI ${toString()} convertTo $measureUnit")
      }
      Double.NaN -> {
        throw NumberFormatException("Division result is not a number! ${toString()} convertTo $measureUnit")
      }
      else -> return Measure(r, measureUnit)
    }
  }

  fun reciprocal(): Measure<T> {
    if (value.testInvalid()) {
      throw NumberFormatException("Division overflow! 1/${toString()}")
    }

    val r: Double = 1.0.div(value)

    if (r.testInvalid()) {
      throw NumberFormatException("Division overflow! 1/${toString()}")
    }
    return Measure(r, unit)
  }

  fun inverse(): Measure<T> {
    if (value.testInvalid()) {
      throw NumberFormatException("Multiplication overflow! -1*${toString()}")
    }

    val r: Double = value.times(-1.0)

    if (r.testInvalid()) {
      throw NumberFormatException("Multiplication overflow! -1*${toString()}")
    }

    return Measure(r, unit)
  }

  operator fun <U : T> plus(measure: Measure<U>): Measure<T> {
    if (measure.unit == unit) {
      val m: Double = value.plus(measure.value)
      return Measure(m, unit)
    }
    if (value.testInvalid() || measure.value.testInvalid()) {
      throw NumberFormatException("Addition overflow! ${toString()} + $measure")
    }

    val r: Double = value.plus(measure.toDouble().div(unit.prefix.multiplier))

    if (r.testInvalid()) {
      throw NumberFormatException("Addition overflow! ${toString()} + $measure")
    }

    return Measure(r, unit)
  }

  operator fun <U : T> minus(measure: Measure<U>): Measure<T> {
    if (measure.unit == unit) {
      val m: Double = value.minus(measure.value)
      return Measure(m, unit)
    }
    if (value.testInvalid() || measure.value.testInvalid()) {
      throw NumberFormatException("Subtraction overflow! ${toString()} + $measure")
    }

    val r: Double = value.minus(measure.toDouble().div(unit.prefix.multiplier))


    if (r.testInvalid()) {
      throw NumberFormatException("Subtraction overflow! ${toString()} + $measure")
    }

    return Measure(r, unit)
  }

  fun scalar(measure: Measure<SI_UNITLESS>): Measure<T> {
    if (value.testInvalid() || measure.value.testInvalid()) {
      throw NumberFormatException("Multiplication overflow! ${toString()} + $measure")
    }

    val r = value.times(measure.toDouble())

    if (r.testInvalid()) {
      throw NumberFormatException("Multiplication overflow! ${toString()} + $measure")
    }

    return Measure(r, unit)
  }

  operator fun <U : BaseUnit> times(measure: Measure<U>): Measure<SI_COMBINED> {
    if (value.testInvalid() || measure.value.testInvalid()) {
      throw NumberFormatException("Multiplication overflow! ${toString()} + $measure")
    }

    val r = toDouble().times(measure.toDouble())

    if (r.testInvalid()) {
//      LOG.warning("Multiplication overflow! ${toString()} * $measure")
      return POSITIVE_INFINITY
    }

    val mu = COMBINED(getQuantity() * measure.getQuantity())
    return Measure(r, mu)
  }

  operator fun times(scalar: Double): Measure<T> {
    if (value.testInvalid()) {
      throw NumberFormatException("Multiplication overflow! ${toString()} * $scalar")
    }

    val r = value.times(scalar)

    if (r.testInvalid()) {
      throw NumberFormatException("Multiplication overflow! ${toString()} * $scalar")
    }

    return Measure(r, unit)
  }

  operator fun div(measure: Measure<T>): Double {
    if (measure.value == 0.0) {
//      LOG.warning("Division by zero! ${toString()}/$measure")
      return Double.NaN
    }
    if (value.testInvalid() || measure.value.testInvalid()) {
      throw NumberFormatException("Division overflow! ${toString()} + $measure")
    }
    if (this.unit is COMBINED) {
      throw NumberFormatException("COMBINED / COMBINED! ${toString()} + $measure")
    }

    return toDouble().div(measure.toDouble())
  }

  operator fun div(x: Double): Measure<T> {
    if (x == 0.0) {
      throw ArithmeticException("Division by zero! ${toString()}/$x")
    }
    if (value.testInvalid() || x.testInvalid()) {
      throw NumberFormatException("Division overflow! ${toString()} + $x")
    }

    val r = toDouble().div(x)

    if (r.testInvalid()) {
      throw ArithmeticException("Division overflow! ${toString()}/$x")
    }

    return Measure(r, unit)
  }

  operator fun <U : BaseUnit> div(measure: Measure<U>): Measure<SI_COMBINED> {
    if (measure.value == 0.0) {
//      LOG.warning("Division by zero! ${toString()}/$measure")
      return NaN
    }
    if (value.testInvalid() || measure.value.testInvalid()) {
      throw NumberFormatException("Division overflow! ${toString()} + $measure")
    }

    val r = toDouble().div(measure.toDouble())

    if (r.testInvalid()) {
//      LOG.warning("Division overflow! ${toString()}/$measure")
      return POSITIVE_INFINITY
    }

    val mu = COMBINED(getQuantity() / measure.getQuantity())
    return Measure(r, mu)
  }

  fun pretty(decimals: Int = 3, padding: Int = 4, optimize: Boolean = true): String {
    if (decimals < 0) {
      throw IllegalArgumentException("Negative decimal places not allowed!")
    }
    if (padding < 0) {
      throw IllegalArgumentException("Negative padding not allowed!")
    }
    var v = value
    var p = unit
    if (optimize) {
      val result = findOptimalPrefix(value, unit.prefix)
      v = result.component1()
      p = MeasureUnit(result.component2(), p.name, p.symbol, p.baseUnit, p.multiplier, p.increment)
    }
    var s = v.format(decimals).replace('.', ',')
    val comma = if (decimals > 0) 1 else 0
    while (s.length < decimals + padding + comma) {
      s = " $s"
    }
    return s + " " + p.toString().replace("Mg", "t")
  }

  private fun findOptimalPrefix(value: Double, prefix: Prefix): Pair<Double, Prefix> {
    var v = value
    var p = prefix
    if (Math.abs(v) < 1.0) {
      while ((Math.abs(v) < 1.0) && (!p.isLast())) {
        v *= (p.up().multiplier / p.multiplier)
        p = p.down()
      }
    } else if (Math.abs(v) >= 1000.0){
      while ((Math.abs(v) >= 1000.0) && (!p.isLast())) {
        v /= (p.up().multiplier / p.multiplier)
        p = p.up()
      }
    }
    return Pair(v, p)
  }

  override fun toString(): String {
    return value.toString() + if (unit.toString() == "") "" else " " + unit.toString()
//    return pretty(optimize = false, padding = 0)
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

  /**
   * Converts value in base units convertTo a Double.
   */
  override fun toDouble(): Double {
      when (value) {
          Double.POSITIVE_INFINITY -> {
//              LOG.warning("POSITIVE_INFINITY! ${toString()}")
              return Double.POSITIVE_INFINITY
          }
          Double.NEGATIVE_INFINITY -> {
//              LOG.warning("NEGATIVE_INFINITY! ${toString()}")
              return Double.NEGATIVE_INFINITY
          }
          Double.NaN -> {
//              LOG.warning("NaN! ${toString()}")
              return Double.NaN
          }
          Double.MAX_VALUE -> return Double.MAX_VALUE
          Double.MIN_VALUE -> return Double.MIN_VALUE
          Double.NaN -> return Double.NaN
          else -> return value.times(unit.prefix.multiplier).times(unit.multiplier)
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

