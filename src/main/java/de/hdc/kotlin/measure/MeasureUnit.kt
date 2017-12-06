package de.hdc.kotlin.measure

import com.danneu.result.Result

open class MeasureUnit<T : SIUnit>(val prefix: Prefix, val unit: DerivedUnit<T>) {

  override fun toString(): String {
    return prefix.toString() + unit.toString()
  }

  override fun equals(other: Any?): Boolean {
    if (other !is MeasureUnit<*>) {
      return false
    } else if ((this.unit is DU_COMBINED) || (other.unit is DU_COMBINED)) {
      return ((this.prefix == other.prefix) && isEquivalentTo(other))
    } else if ((this.prefix == other.prefix) && (this.unit == other.unit)) {
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
    return (this.unit.baseUnit.quantity == other.unit.baseUnit.quantity)
  }

  companion object MOLE : MeasureUnit<SI_MOLE>(Prefix.NONE, DU_MOLE)

}

  fun measureUnitFrom(value: String): Result<*, *> {
      var v = value.trim()
      // Unitless
      if (v.isEmpty()) {
          return Result.ok(UNITLESS)
      }
      // Prefix
      var prefix = Prefix.NONE
      if (v.length > 1) {
        val ps = v[0]
        for(p in Prefix.values()) {
          if (ps == p.symbol) {
            prefix = p
            v = v.substring(1)
          }
        }
      }
      // Unit
      return measureUnits
              .firstOrNull { v == it.unit.symbol }
              ?.let { Result.ok(MeasureUnit(prefix, it.unit)) }
              ?: Result.err("Could not parse value!")
  }

//Measure Units for convenience
class COMBINED(q: Quantity) : MeasureUnit<SI_COMBINED>(Prefix.NONE, DU_COMBINED(q))

object UNITLESS : MeasureUnit<SI_UNITLESS>(Prefix.NONE, DU_UNITLESS)

object MOLE : MeasureUnit<SI_MOLE>(Prefix.NONE, DU_MOLE)
object AMPERE : MeasureUnit<SI_AMPERE>(Prefix.NONE, DU_AMPERE)
object METER : MeasureUnit<SI_METER>(Prefix.NONE, DU_METER)
object SQUARE_METER : MeasureUnit<SI_AREA>(Prefix.NONE, DU_SQUARE_METER)
object CUBIC_METER : MeasureUnit<SI_VOLUME>(Prefix.NONE, DU_CUBIC_METER)
object CANDELA : MeasureUnit<SI_CANDELA>(Prefix.NONE, DU_CANDELA)
object GRAM : MeasureUnit<SI_GRAM>(Prefix.NONE, DU_GRAM)
object KELVIN : MeasureUnit<SI_KELVIN>(Prefix.NONE, DU_KELVIN)
object SECOND : MeasureUnit<SI_SECOND>(Prefix.NONE, DU_SECOND)
object RADIAN : MeasureUnit<SI_RADIAN>(Prefix.NONE, DU_RADIAN)
object STERADIAN : MeasureUnit<SI_STERADIAN>(Prefix.NONE, DU_STERADIAN)
object HERTZ : MeasureUnit<SI_HERTZ>(Prefix.NONE, DU_HERTZ)
object NEWTON : MeasureUnit<SI_NEWTON>(Prefix.NONE, DU_NEWTON)
object PASCAL : MeasureUnit<SI_PASCAL>(Prefix.NONE, DU_PASCAL)
object JOULE : MeasureUnit<SI_JOULE>(Prefix.NONE, DU_JOULE)
object WATT : MeasureUnit<SI_WATT>(Prefix.NONE, DU_WATT)
//Other
object MINUTE : MeasureUnit<SI_SECOND>(Prefix.NONE, DerivedUnit("Minute", "min", SI_SECOND, 60.0))
object HOUR : MeasureUnit<SI_SECOND>(Prefix.NONE, DerivedUnit("Hour", "h", SI_SECOND, 3600.0))
object DAY : MeasureUnit<SI_SECOND>(Prefix.NONE, DerivedUnit("Day", "d", SI_SECOND, 24.0 * 3600.0))
object AU : MeasureUnit<SI_METER>(Prefix.NONE, DerivedUnit("Astronomical Unit", "AU", SI_METER, 149597870700.0))
object LIGHT_YEAR : MeasureUnit<SI_METER>(Prefix.NONE, DerivedUnit("Light Year", "ly", SI_METER, 9460730472580800.0))
object PARSEC : MeasureUnit<SI_METER>(Prefix.NONE, DerivedUnit("Parsec", "pc", SI_METER, 3.08567758149137e16))
object LITRE : MeasureUnit<SI_VOLUME>(Prefix.NONE, DerivedUnit("Litre", "L", SI_VOLUME, 1e-3))
object KILO_GRAM : MeasureUnit<SI_GRAM>(Prefix.KILO, DU_GRAM)
object TON : MeasureUnit<SI_GRAM>(Prefix.NONE, DerivedUnit("Ton", "t", SI_GRAM, 1e6))
object KILO_METER : MeasureUnit<SI_METER>(Prefix.KILO, DU_METER)
object GRADE_CELSIUS : MeasureUnit<SI_KELVIN>(Prefix.NONE, DerivedUnit("Celsius", "°C", SI_KELVIN, 1.0, -273.15))
object M_PER_S : MeasureUnit<SI_VELOCITY>(Prefix.NONE, DerivedUnit("Velocity", "m/s", SI_VELOCITY))
object KM_PER_H : MeasureUnit<SI_VELOCITY>(Prefix.NONE, DerivedUnit("Velocity", "km/h", SI_VELOCITY, 3.6))
object ACCELERATION : MeasureUnit<SI_ACCELERATION>(Prefix.NONE, DerivedUnit("Acceleration", "m/s²", SI_ACCELERATION))

val measureUnits: MutableList<MeasureUnit<out SIUnit>> = mutableListOf(
    UNITLESS, MOLE, AMPERE, METER, SQUARE_METER, CUBIC_METER, CANDELA, GRAM, KELVIN
    , SECOND, RADIAN, STERADIAN, HERTZ, NEWTON, PASCAL, JOULE, WATT
    , MINUTE, HOUR, DAY, AU, LIGHT_YEAR, PARSEC, LITRE, KILO_GRAM, GRADE_CELSIUS
    , M_PER_S, KM_PER_H, ACCELERATION
    )
