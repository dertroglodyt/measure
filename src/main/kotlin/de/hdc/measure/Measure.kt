package de.hdc.measure

import ch.obermuhlner.math.big.*
import ch.obermuhlner.math.big.BigFloat.*
import ch.obermuhlner.math.big.kotlin.*
import kotlin.Float
import kotlin.Short

//TODO
//fun <T : BaseUnit> bigMeasureFrom(value: String): Result<Measure<T>, *> {
//  throw NotImplementedError()
//    var v = value
  // numeric value
//    if (v.indexOf(" ") < 0) {
//        return Measure(Double.valueOf(v), UNITLESS)
//    }
  // Unitless
  // Prefix
  // Unit
//  return Result.ok(value)
//}

/*
 * Combines a value with a physical unit.
 */
data class Measure<T : BaseUnit>(
  val value: BigFloat,
  val unit: MeasureUnit<T>
) : Number(), Comparable<Measure<T>> {

  constructor (
    value: Double,
    prefix: Prefix = Prefix.NONE,
    unit: MeasureUnit<T>
  )
      : this(defaultContext.valueOf(value), MeasureUnit(prefix, unit))

  constructor (
    value: Double,
    unit: MeasureUnit<T>
  )
      : this(defaultContext.valueOf(value), MeasureUnit(Prefix.NONE, unit))

  companion object {
    val defaultContext: BigFloat.Context =
      context(42) ?: error("Could not initialize math context!")
    val ZERO = Measure(0.0, UNITLESS)
    val ONE = Measure(1.0, UNITLESS)
    val MINUS_ONE = Measure(-1.0, UNITLESS)
    val ONE_THAUSEND = Measure(1000.0, UNITLESS)
//    val NaN = Measure(Double.NaN, unit = COMBINED(Quantity()))
  }

  private fun getQuantity(): Quantity {
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
//    if (other !is Measure<*>) {
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

  fun aproximates(other: Measure<T>, maxDiff: Measure<SI_UNITLESS>): Boolean {
    if (!unit.isEquivalentTo(other.unit)) {
      return false
    }
    if (unit == other.unit) {
      return value.approximates(other.value, maxDiff.value)
    }
    return value.approximates(other.convertTo(unit).value, maxDiff.value)
  }

  fun <U : BaseUnit> convertTo(measureUnit: MeasureUnit<U>): Measure<U> {
    if (measureUnit == unit) {
      // Don't create a copy of this Measure
//      return Measure(value, measureUnit)
      @Suppress("UNCHECKED_CAST")
      return this as Measure<U>
    }
    if (!measureUnit.isEquivalentTo(this.unit)) {
      throw java.lang.IllegalArgumentException("Inconvertible units! ${toString()} convertTo $measureUnit")
    }
    // TODO multiply by unit.prefix.multiplier and unit.multiplier?
    val r = value
      .times(unit.prefix.multiplier)
      .subtract(unit.increment)
      .times(unit.multiplier)

      .div(measureUnit.multiplier)
      .plus(measureUnit.increment)
      .div(measureUnit.prefix.multiplier)
    return Measure(r, measureUnit)
  }


  fun reciprocal(): Measure<SI_COMBINED> = Measure(ONE.value.div(value), unit.reciprocal())

  fun inverse(): Measure<T> = Measure(value.times(MINUS_ONE.value), unit)

  operator fun <U : T> plus(measure: Measure<U>): Measure<T>
      = Measure(value + measure.convertTo(unit).value, unit)

  operator fun <U : T> minus(measure: Measure<U>): Measure<T>
      = Measure(value - measure.convertTo(unit).value, unit)

  fun scalarTimes(measure: Measure<SI_UNITLESS>): Measure<T>
      = Measure(fromBaseUnit(toBaseUnit() * measure.toBaseUnit()), unit)

  operator fun <U : BaseUnit> times(measure: Measure<U>): Measure<SI_COMBINED> {
    val mu = COMBINED(getQuantity() * measure.getQuantity())
    return Measure(fromBaseUnit(toBaseUnit() * measure.toBaseUnit()), mu)
  }

  operator fun times(scalar: Double): Measure<T> = Measure(value.times(scalar), unit)

  fun scalarDiv(measure: Measure<SI_UNITLESS>): Measure<T>
      = Measure(fromBaseUnit(toBaseUnit() / measure.toBaseUnit()), unit)

//  operator fun div(measure: Measure<T>): BigFloat {
//    return toBaseUnit() / measure.toBaseUnit()
//  }

  operator fun div(x: Double): Measure<T> = Measure(value / x, unit)

  operator fun <U : BaseUnit> div(measure: Measure<U>): Measure<SI_COMBINED> {
    val r = toBaseUnit() * measure.toBaseUnit()
    val mu = COMBINED(getQuantity() / measure.getQuantity())
    return Measure(r, mu)
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
    return value.toString() + if (unit.toString().isEmpty()) "" else " $unit"
//    return format(optimize = false, padding = 0)
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

  private fun fromBaseUnit(x: BigFloat): BigFloat {
    return ((x / unit.multiplier) + unit.increment) / unit.prefix.multiplier
  }

  private fun toBaseUnit(): BigFloat {
    return ((value * unit.prefix.multiplier) - unit.increment) * unit.multiplier
  }

  /**
   * Converts value in base units convertTo a Double.
   */
  override fun toDouble(): Double = toBaseUnit().toDouble()

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

  fun asFlux(): Measure<SI_FLUX<T>> {
    return Measure(value, Flux(unit))
  }
}

