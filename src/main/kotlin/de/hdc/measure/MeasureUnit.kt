package de.hdc.measure

import de.hdc.measure.Prefix.NONE

open class MeasureUnit<out T : BaseUnit>(
        val prefix: Prefix,
        val name: String,
        val symbol: String,
        val baseUnit: T,
        val multiplier: Double = 1.0,
        val increment: Double = 0.0) {

  override fun toString(): String {
    return if (this is COMBINED) {
      prefix.toString() + baseUnit.quantity.toString()
    } else {
      prefix.toString() + symbol
    }
  }

  override fun equals(other: Any?): Boolean {
    if (other !is MeasureUnit<*>) {
      return false
    } else if ((this.baseUnit is SI_COMBINED) || (other.baseUnit is SI_COMBINED)) {
      return ((this.prefix == other.prefix) && isEquivalentTo(other))
    } else if ((this.prefix == other.prefix) && (this.baseUnit == other.baseUnit)
            && (this.increment == other.increment) && (this.multiplier == other.multiplier)) {
      return true
    }
    return false
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

//  companion object MOLE : MeasureUnit<SI_MOLE>(NONE, "Mole", "SI_MOLE", SI_MOLE)

}

//todo
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

//Measure Units for convenience
class COMBINED(q: Quantity) : MeasureUnit<SI_COMBINED>(NONE, "", "", SI_COMBINED(q))

object UNITLESS : MeasureUnit<SI_UNITLESS>(NONE, "", "", SI_UNITLESS)

object mol : MeasureUnit<SI_MOLE>(NONE, "Mole", "SI_MOLE", SI_MOLE)
object A : MeasureUnit<SI_AMPERE>(NONE, "Ampere", "A", SI_AMPERE)
object m : MeasureUnit<SI_METER>(NONE, "Meter", "m", SI_METER)
object m2 : MeasureUnit<SI_AREA>(NONE, "Square Meter", "m²", SI_AREA)
object m3 : MeasureUnit<SI_VOLUME>(NONE, "Cubic Meter", "m³", SI_VOLUME)
object Cd : MeasureUnit<SI_CANDELA>(NONE, "Candela", "cd", SI_CANDELA)
object g : MeasureUnit<SI_GRAM>(NONE, "Gram", "g", SI_GRAM)
object K : MeasureUnit<SI_KELVIN>(NONE, "Kelvin", "K", SI_KELVIN)
object s : MeasureUnit<SI_SECOND>(NONE, "Second", "s", SI_SECOND)
object rad : MeasureUnit<SI_RADIAN>(NONE, "Radian", "rad", SI_RADIAN)
object sr : MeasureUnit<SI_STERADIAN>(NONE, "Steradian", "sr", SI_STERADIAN)
object Hz : MeasureUnit<SI_HERTZ>(NONE, "Hertz", "Hz", SI_HERTZ)
object N : MeasureUnit<SI_NEWTON>(NONE, "Newton", "N", SI_NEWTON, 1e3)

object Pa : MeasureUnit<SI_PASCAL>(NONE, "Pascal", "PA", SI_PASCAL)
object J : MeasureUnit<SI_JOULE>(NONE, "Joule", "NumberName", SI_JOULE)
object W : MeasureUnit<SI_WATT>(NONE, "Watt", "W", SI_WATT)
//Other
object min : MeasureUnit<SI_SECOND>(NONE, "Minute", "min", SI_SECOND, 60.0)
object h : MeasureUnit<SI_SECOND>(NONE, "Hour", "h", SI_SECOND, 3600.0)
object d : MeasureUnit<SI_SECOND>(NONE, "Day", "d", SI_SECOND, 24.0 * 3600.0)
object AU : MeasureUnit<SI_METER>(NONE, "Astronomical Unit", "AU", SI_METER, 149597870700.0)
object ly : MeasureUnit<SI_METER>(NONE, "Light Year", "ly", SI_METER, 9460730472580800.0)
object pc : MeasureUnit<SI_METER>(NONE, "Parsec", "pc", SI_METER, 3.08567758149137e16)
object L : MeasureUnit<SI_VOLUME>(NONE, "Litre", "L", SI_VOLUME, 1e-3)
object kg : MeasureUnit<SI_GRAM>(Prefix.KILO, "Gram", "g", SI_GRAM)
object t : MeasureUnit<SI_GRAM>(NONE, "Ton", "t", SI_GRAM, 1e6)
object `℃` : MeasureUnit<SI_KELVIN>(NONE, "Celsius", "°C", SI_KELVIN, 1.0, -273.15)
object m_s : MeasureUnit<SI_VELOCITY>(NONE, "Velocity", "m/s", SI_VELOCITY)
object km_h : MeasureUnit<SI_VELOCITY>(NONE, "Velocity", "km/h", SI_VELOCITY, 3.6)
object m_s2 : MeasureUnit<SI_ACCELERATION>(NONE, "Acceleration", "m/s²", SI_ACCELERATION)

val MEASURE_UNITS: MutableList<MeasureUnit<BaseUnit>> = mutableListOf(
        UNITLESS, mol, A, m, m2, m3, Cd, g, K
        , s, rad, sr, Hz, N, Pa, J, W
        , min, h, d, AU, ly, pc, L, kg, t, `℃`
        , m_s, km_h, m_s2
)
