package de.hdc.measure

import ch.obermuhlner.math.big.*
import ch.obermuhlner.math.big.BigFloat.*
import de.hdc.measure.Prefix.NONE

typealias MU<T> = MeasureUnit<T>

open class MeasureUnit<out T : BaseUnit>(
  val prefix: Prefix,
  val name: String,
  val symbol: String,
  val baseUnit: T,
  val multiplier: BigFloat = context(1).valueOf(1),
  val increment: BigFloat = context(1).valueOf(0)) {

  constructor(prefix: Prefix, unit: MeasureUnit<T>) :
          this(prefix, unit.name, unit.symbol, unit.baseUnit, unit.multiplier, unit.increment)

  override fun toString(): String {
    return if (this is COMBINED) {
      prefix.toString() + baseUnit.quantity.toString()
    } else {
      prefix.toString() + symbol
    }
  }

  override fun equals(other: Any?): Boolean {
    return when {
      (other !is MeasureUnit<*>)
          -> false
      (this.baseUnit is SI_COMBINED || other.baseUnit is SI_COMBINED)
              && (this.prefix == other.prefix)
              && (this.increment == other.increment)
              && (this.multiplier == other.multiplier)
          -> isEquivalentTo(other)
      (this.prefix == other.prefix)
              && (this.baseUnit == other.baseUnit)
              && (this.increment == other.increment)
              && (this.multiplier == other.multiplier)
          -> true
      else -> false
    }
  }

  override fun hashCode(): Int {
    return super.hashCode()
  }

  /*
   * Test if Quantities of units are the same.
   */
  fun isEquivalentTo(other: MeasureUnit<*>): Boolean {
    return (this.baseUnit.quantity == other.baseUnit.quantity)
  }

  fun setPrefix(newPrefix: Prefix): MeasureUnit<T> {
    return MeasureUnit(newPrefix, name, symbol, baseUnit, multiplier, increment)
  }

fun reciprocal(): MeasureUnit<SI_COMBINED> {
  return COMBINED(baseUnit.quantity.reciprocal())
}

//  companion object mol: MeasureUnit<SI_MOLE>(NONE, "Mole", "SI_MOLE", SI_MOLE)

}

/**todo
//fun measureUnitFrom(value: String): Result<MeasureUnit<*>, *> {
//  var v = value.trim()
//  // Unitless
//  if (v.isEmpty()) {
//    return Result.ok(UNITLESS)
//  }
//  // Prefix
//  var prefix = NONE
//  if (v.length > 1) {
//    val ps = v[0]
//    for (p in Prefix.values()) {
//      if (ps == p.symbol) {
//        prefix = p
//        v = v.substring(1)
//      }
//    }
//  }
//  // Unit
//  return MEASURE_UNITS
//          .firstOrNull { v == it.symbol }
//          ?.let { Result.ok(MeasureUnit(prefix, it.name, it.symbol, it.baseUnit)) }
//          ?: Result.err("Could not parse value!<$value>")
 //}
**/

//Measure Units for convenience
class COMBINED(q: Quantity) : MeasureUnit<SI_COMBINED>(NONE, "combined", q.toString(), SI_COMBINED(q))

object UNITLESS : MeasureUnit<SI_UNITLESS>(NONE, "", "", SI_UNITLESS)

object mol : MeasureUnit<SI_MOLE>(NONE, "Mole", "SI_MOLE", SI_MOLE)
object A : MeasureUnit<SI_AMPERE>(NONE, "Ampere", "A", SI_AMPERE)
object m : MeasureUnit<SI_METER>(NONE, "Meter", "m", SI_METER)
object m2 : MeasureUnit<SI_AREA>(NONE, "Square Meter", "m²", SI_AREA)
object m3 : MeasureUnit<SI_VOLUME>(NONE, "Cubic Meter", "m³", SI_VOLUME)
object Cd : MeasureUnit<SI_CANDELA>(NONE, "Candela", "cd", SI_CANDELA)
object g : MeasureUnit<SI_KILOGRAM>(NONE, "Gram", "g", SI_KILOGRAM,
  multiplier = context(1).valueOf(1e-3))
object K : MeasureUnit<SI_KELVIN>(NONE, "Kelvin", "K", SI_KELVIN)
object s : MeasureUnit<SI_SECOND>(NONE, "Second", "s", SI_SECOND)
object rad : MeasureUnit<SI_RADIAN>(NONE, "Radian", "rad", SI_RADIAN)
object sr : MeasureUnit<SI_STERADIAN>(NONE, "Steradian", "sr", SI_STERADIAN)
object Hz : MeasureUnit<SI_HERTZ>(NONE, "Hertz", "Hz", SI_HERTZ)
object N : MeasureUnit<SI_NEWTON>(NONE, "Newton", "N", SI_NEWTON)

object pcs : MeasureUnit<SI_PCS>(NONE, "pieces", "pcs", SI_PCS)
object Pa : MeasureUnit<SI_PASCAL>(NONE, "Pascal", "PA", SI_PASCAL)
object J : MeasureUnit<SI_JOULE>(NONE, "Joule", "NumberName", SI_JOULE)
object W : MeasureUnit<SI_WATT>(NONE, "Watt", "W", SI_WATT)
//Other
object min : MeasureUnit<SI_SECOND>(NONE, "Minute", "min", SI_SECOND,
  multiplier = context(2).valueOf(60.0))
object h : MeasureUnit<SI_SECOND>(NONE, "Hour", "h", SI_SECOND,
  multiplier = context(4).valueOf(3600.0))
object d : MeasureUnit<SI_SECOND>(NONE, "Day", "d", SI_SECOND,
  multiplier = context(5).valueOf(24.0 * 3600.0))
object AU : MeasureUnit<SI_METER>(NONE, "Astronomical Unit", "AU", SI_METER,
  multiplier = context(12).valueOf(149597870700))
object ly : MeasureUnit<SI_METER>(NONE, "Light Year", "ly", SI_METER,
  multiplier = context(16).valueOf(9460730472580800))
object pc : MeasureUnit<SI_METER>(NONE, "Parsec", "pc", SI_METER,
  multiplier = context(16).valueOf(3.08567758149137e16))
object L : MeasureUnit<SI_VOLUME>(NONE, "Litre", "L", SI_VOLUME,
  multiplier = context(3).valueOf(1e-3))
//object kg : MeasureUnit<SI_KILOGRAM>(Prefix.k, "Kilogram", "kg", SI_KILOGRAM)
object t : MeasureUnit<SI_KILOGRAM>(NONE, "Ton", "t", SI_KILOGRAM,
  multiplier = context(3).valueOf(1e3))
object `°C` : MeasureUnit<SI_KELVIN>(NONE, "Celsius", "°C", SI_KELVIN,
  increment = context(5).valueOf(-273.15))
object m_s : MeasureUnit<SI_VELOCITY>(NONE, "Velocity", "m/s", SI_VELOCITY)
object km_h : MeasureUnit<SI_VELOCITY>(NONE, "Velocity", "km/h", SI_VELOCITY,
  multiplier = context(2).valueOf(3.6))
object m_s2 : MeasureUnit<SI_ACCELERATION>(NONE, "Acceleration", "m/s²", SI_ACCELERATION)
object `°`: MeasureUnit<SI_RADIAN>(NONE, "Degree", "°", SI_RADIAN,
  multiplier = BigMeasure.defaultContext.valueOf(Math.PI / 180.0))
object rad_s : MeasureUnit<SI_ANGULAR_VELOCITY>(NONE, "Angular Velocity", "rad/s", SI_ANGULAR_VELOCITY)

//val MEASURE_UNITS: MutableList<MeasureUnit<BaseUnit>> = mutableListOf(
//        UNITLESS, mol, A, m, m2, m3, Cd, kg, K
//        , s, rad, sr, Hz, N, Pa, J, W
//        , min, h, d, AU, ly, pc, L, kg, t, `°C`
//        , m_s, km_h, m_s2
//)
