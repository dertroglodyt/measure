package de.hdc.measure

import ch.obermuhlner.math.big.*
import ch.obermuhlner.math.big.BigFloat.*
import ch.obermuhlner.math.big.kotlin.*
import com.danneu.result.*

  //TODO
//fun <T : BaseUnit> bigMeasureFrom(value: String): Result<BigMeasure<T>, *> {
//  throw NotImplementedError()
//    var v = value
  // numeric value
//    if (v.indexOf(" ") < 0) {
//        return BigMeasure(Double.valueOf(v), UNITLESS)
//    }
  // Unitless
  // Prefix
  // Unit
//  return Result.ok(value)
//}

/*
 * Combines a value with a physical unit.
 */
data class BigMeasure<T : BaseUnit>(
  val value: BigFloat,
  val unit: MeasureUnit<T>
) : Number(), Comparable<BigMeasure<T>> {

  constructor (
    value: Double,
    prefix: Prefix = Prefix.NONE,
    unit: MeasureUnit<T>,
    context: BigFloat.Context = defaultContext
  )
      : this(context.valueOf(value), MeasureUnit(prefix, unit))

  constructor (
    value: Double,
    unit: MeasureUnit<T>,
    context: BigFloat.Context = defaultContext
  )
      : this(context.valueOf(value), MeasureUnit(Prefix.NONE, unit))

  companion object {
    val defaultContext: BigFloat.Context =
      context(42) ?: error("Could not initialize math context!")
    val ZERO = BigMeasure(0.0, UNITLESS)
    val ONE = BigMeasure(1.0, UNITLESS)
    val MINUS_ONE = BigMeasure(-1.0, UNITLESS)
    val ONE_THAUSEND = BigMeasure(1000.0, UNITLESS)
//    val NaN = BigMeasure(Double.NaN, unit = COMBINED(Quantity()))
  }

  private fun getQuantity(): Quantity {
    return unit.baseUnit.quantity
  }

  //todo
  /**
  //  fun <U : T> convertTo(measureUnit: MeasureUnit<U>): BigMeasure<U> {
  //    if (measureUnit == unit) {
  //      return BigMeasure(value, measureUnit)
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
  //          else -> return BigMeasure(r, measureUnit)
  //      }
  //  }
   */

  override fun equals(other: Any?): Boolean {
    if (other !is BigMeasure<*>) {
      return false
    }
    if (!unit.isEquivalentTo(other.unit)) {
      return false
    }
    if (unit == other.unit) {
      return value.isEqual(other.value)
    }
    return convertTo(other.unit).value.isEqual(other.value)
//    return toDouble() == other.convertTo(this.unit).toDouble()
  }

  override fun hashCode(): Int {
    return super.hashCode()
  }

//  infix fun aproximates(other: Any?): Boolean {
//    if (other !is BigMeasure<*>) {
//      return false
//    }
//    if (!unit.isEquivalentTo(other.unit)) {
//      return false
//    }
//    if (unit == other.unit) {
//      return value aproximates other.value
//    }
//    return value aproximates other.convertTo(unit)
//  }

  fun aproximates(other: BigMeasure<T>, maxDiff: BigMeasure<T>): Boolean {
    if (!unit.isEquivalentTo(other.unit)) {
      return false
    }
    if (unit == other.unit) {
      return aproximates(other, maxDiff.convertTo(unit))
    }
    return aproximates(other.convertTo(unit), maxDiff.convertTo(unit))
  }

  fun <U : BaseUnit> convertTo(measureUnit: MeasureUnit<U>): BigMeasure<U> {
    if (measureUnit == unit) {
      return this as BigMeasure<U>
//      return BigMeasure(value, measureUnit)
    }
    if (!measureUnit.isEquivalentTo(this.unit)) {
      throw java.lang.IllegalArgumentException("Inconvertible units! ${toString()} convertTo $measureUnit")
    }
    // TODO multiply by unit.prefix.multiplier and unit.multiplier?
    val r = value.subtract(unit.increment)
      .div(measureUnit.prefix.multiplier)
      .div(measureUnit.multiplier)
      .times(unit.prefix.multiplier)
      .times(unit.multiplier)
      .plus(measureUnit.increment)
    return BigMeasure(r, measureUnit)
  }


  fun reciprocal(): BigMeasure<SI_COMBINED> = BigMeasure(ONE.value.div(value), unit.reciprocal())

  fun inverse(): BigMeasure<T> = BigMeasure(value.times(MINUS_ONE.value), unit)

  operator fun <U : T> plus(measure: BigMeasure<U>): BigMeasure<T>
      = BigMeasure(value + measure.convertTo(unit).value, unit)

  operator fun <U : T> minus(measure: BigMeasure<U>): BigMeasure<T>
      = BigMeasure(value - measure.convertTo(unit).value, unit)

  // TODO cant convert unitless to unit
  fun scalar(measure: BigMeasure<SI_UNITLESS>): BigMeasure<T>
      = BigMeasure(value * measure.convertTo(unit).value, unit)

  operator fun <U : BaseUnit> times(measure: BigMeasure<U>): BigMeasure<SI_COMBINED> {
    val r = value * measure.convertTo(unit).value
    val mu = COMBINED(getQuantity() * measure.getQuantity())
    return BigMeasure(r, mu)
  }

  operator fun times(scalar: Double): BigMeasure<T> = BigMeasure(value.times(scalar), unit)

  operator fun div(measure: BigMeasure<T>): Double {

    if (this.unit is COMBINED) {
      throw NumberFormatException("COMBINED / Any! ${toString()} + $measure")
    }

    return BigMeasure(value / measure.convertTo(unit).value, unit).toDouble()
  }

  operator fun div(x: Double): BigMeasure<T> = BigMeasure(value / x, unit)

  operator fun <U : BaseUnit> div(measure: BigMeasure<U>): BigMeasure<SI_COMBINED> {
    val r = value * measure.convertTo(unit).value
    val mu = COMBINED(getQuantity() / measure.getQuantity())
    return BigMeasure(r, mu)
  }

  /**
   * @param decimals Digits to show after decimal seperator
   * @param padding Prepend with at most this number of Spaces if less digits on left side
   *    of decimal seperator.
   * @param optimize Wether to replace the prefix of the unit to shorten displayed number or
   *     leave it as is.
   */
  fun format(decimals: Int = 3, padding: Int = 4, optimize: Boolean = true): String {
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
      v = result.first
      p = p.setPrefix(result.second)
    }
    var s = v.toDouble().format(decimals)
    var x = s.indexOf(',')
    if (x < 0) {
      x = s.length
    }
    x = padding - x
    if (x > 0) {
      for (i in 1..x) {
        s = " $s"
      }
    }
    return s + if (p.toString().isEmpty()) "" else " " + p.toString().replace("Mg", "t")
  }

  private fun findOptimalPrefix(value: BigFloat, prefix: Prefix): Pair<BigFloat, Prefix> {
    var v = value
    var p = prefix
    if (BigFloat.abs(v).isLessThan(ONE.value) && (v != ZERO)) {
      while (BigFloat.abs(v).isLessThan(ONE.value) && (!p.isLast())) {
        v *= (p.up().multiplier / p.multiplier)
        p = p.down()
      }
    } else if (BigFloat.abs(v).isGreaterThanOrEqual(ONE_THAUSEND.value)) {
      while (BigFloat.abs(v).isGreaterThanOrEqual(ONE_THAUSEND.value) && (!p.isLast())) {
        v /= (p.up().multiplier / p.multiplier)
        p = p.up()
      }
    }
    return Pair(v, p)
  }

  override fun toString(): String {
    return value.toString() + if (unit.toString().isEmpty()) "" else " " + unit.toString()
//    return format(optimize = false, padding = 0)
  }

  override fun compareTo(other: BigMeasure<T>): Int {
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
  override fun toDouble(): Double = value
    .times(unit.prefix.multiplier)
    .times(unit.multiplier)
    .minus(unit.increment).toDouble()

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

