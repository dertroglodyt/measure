package de.hdc.kotlin.measure

import com.danneu.result.*

open class MeasureUnit<out T: Quantity>(val prefix: Prefix, val unit: DerivedUnit<T>) {

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

  companion object MOLE : MeasureUnit<AMOUNT>(Prefix.NONE, DU_MOLE)

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
class COMBINED(q: UNKNOWN) : MeasureUnit<UNKNOWN>(Prefix.NONE, DU_COMBINED(q))

object UNITLESS : MeasureUnit<Q_UNITLESS>(Prefix.NONE, DU_UNITLESS)

object MOLE : MeasureUnit<AMOUNT>(Prefix.NONE, DU_MOLE)
object AMPERE : MeasureUnit<ELECTRIC_CURRENT>(Prefix.NONE, DU_AMPERE)
object METER : MeasureUnit<LENGTH>(Prefix.NONE, DU_METER)
object SQUARE_METER : MeasureUnit<AREA>(Prefix.NONE, DU_SQUARE_METER)
object CUBIC_METER : MeasureUnit<VOLUME>(Prefix.NONE, DU_CUBIC_METER)
object CANDELA : MeasureUnit<LUMINOUS_INTENSITY>(Prefix.NONE, DU_CANDELA)
object GRAM : MeasureUnit<MASS>(Prefix.NONE, DU_GRAM)
object KELVIN : MeasureUnit<THERMODYNAMIC_TEMPERATURE>(Prefix.NONE, DU_KELVIN)
object SECOND : MeasureUnit<TIME>(Prefix.NONE, DU_SECOND)
object RADIAN : MeasureUnit<ANGLE>(Prefix.NONE, DU_RADIAN)
object STERADIAN : MeasureUnit<ANGLE>(Prefix.NONE, DU_STERADIAN)
object HERTZ : MeasureUnit<FREQUENCY>(Prefix.NONE, DU_HERTZ)
object NEWTON : MeasureUnit<FORCE>(Prefix.NONE, DU_NEWTON)
object PASCAL : MeasureUnit<PRESSURE>(Prefix.NONE, DU_PASCAL)
object JOULE : MeasureUnit<WORK>(Prefix.NONE, DU_JOULE)
object WATT : MeasureUnit<POWER>(Prefix.NONE, DU_WATT)
//Other
object MINUTE : MeasureUnit<TIME>(Prefix.NONE, DerivedUnit("Minute", "min", SI_SECOND, 60.0))
object HOUR : MeasureUnit<TIME>(Prefix.NONE, DerivedUnit("Hour", "h", SI_SECOND, 3600.0))
object DAY : MeasureUnit<TIME>(Prefix.NONE, DerivedUnit("Day", "d", SI_SECOND, 24.0 * 3600.0))
object AU : MeasureUnit<LENGTH>(Prefix.NONE, DerivedUnit("Astronomical Unit", "AU", SI_METER, 149597870700.0))
object LIGHT_YEAR : MeasureUnit<LENGTH>(Prefix.NONE, DerivedUnit("Light Year", "ly", SI_METER, 9460730472580800.0))
object PARSEC : MeasureUnit<LENGTH>(Prefix.NONE, DerivedUnit("Parsec", "pc", SI_METER, 3.08567758149137e16))
object LITRE : MeasureUnit<VOLUME>(Prefix.NONE, DerivedUnit("Litre", "L", SI_VOLUME, 1e-3))
object KILO_GRAM : MeasureUnit<MASS>(Prefix.KILO, DU_GRAM)
object TON : MeasureUnit<MASS>(Prefix.NONE, DerivedUnit("Ton", "t", SI_GRAM, 1e6))
object KILO_METER : MeasureUnit<LENGTH>(Prefix.KILO, DU_METER)
object GRADE_CELSIUS : MeasureUnit<THERMODYNAMIC_TEMPERATURE>(Prefix.NONE, DerivedUnit("Celsius", "°C", SI_KELVIN, 1.0, -273.15))
object M_PER_S : MeasureUnit<VELOCITY>(Prefix.NONE, DerivedUnit("Velocity", "m/s", SI_VELOCITY))
object KM_PER_H : MeasureUnit<VELOCITY>(Prefix.NONE, DerivedUnit("Velocity", "km/h", SI_VELOCITY, 3.6))
object ACCELERATION : MeasureUnit<Q_ACCELERATION>(Prefix.NONE, DerivedUnit("Acceleration", "m/s²", SI_ACCELERATION))

val measureUnits: MutableList<MeasureUnit<Quantity>> = mutableListOf(
    UNITLESS, MOLE, AMPERE, METER, SQUARE_METER, CUBIC_METER, CANDELA, GRAM, KELVIN
    , SECOND, RADIAN, STERADIAN, HERTZ, NEWTON, PASCAL, JOULE, WATT
    , MINUTE, HOUR, DAY, AU, LIGHT_YEAR, PARSEC, LITRE, KILO_GRAM, GRADE_CELSIUS
    , M_PER_S, KM_PER_H, ACCELERATION
    )
