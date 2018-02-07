package de.hdc.kotlin.measure

sealed class SIUnit<out T: Quantity>(val quantity: T)

// SI Units
class SI_COMBINED(q: UNKNOWN) : SIUnit<UNKNOWN>(UNKNOWN(q))

object SI_UNITLESS : SIUnit<Q_UNITLESS>(Q_UNITLESS)
object SI_MOLE : SIUnit<AMOUNT>(AMOUNT)
object SI_AMPERE : SIUnit<ELECTRIC_CURRENT>(ELECTRIC_CURRENT)
object SI_METER : SIUnit<LENGTH>(LENGTH)
object SI_CANDELA : SIUnit<LUMINOUS_INTENSITY>(LUMINOUS_INTENSITY)
object SI_GRAM : SIUnit<MASS>(MASS)
object SI_KELVIN : SIUnit<THERMODYNAMIC_TEMPERATURE>(THERMODYNAMIC_TEMPERATURE)
object SI_SECOND : SIUnit<TIME>(TIME)
// Derived SI Units
object SI_AREA : SIUnit<AREA>(AREA)
object SI_VOLUME : SIUnit<VOLUME>(VOLUME)
object SI_RADIAN : SIUnit<ANGLE>(ANGLE)
object SI_STERADIAN : SIUnit<ANGLE>(ANGLE)
object SI_HERTZ : SIUnit<FREQUENCY>(FREQUENCY)
object SI_NEWTON : SIUnit<FORCE>(FORCE)
object SI_PASCAL : SIUnit<PRESSURE>(PRESSURE)
object SI_JOULE : SIUnit<WORK>(WORK)
object SI_WATT : SIUnit<POWER>(POWER)
object SI_VELOCITY : SIUnit<VELOCITY>(VELOCITY)
object SI_ACCELERATION : SIUnit<Q_ACCELERATION>(Q_ACCELERATION)
//...

open class DerivedUnit<out T: Quantity>(
    val longName: String,
    val symbol: String,
    val baseUnit: SIUnit<T>,
    val multiplier: Double = 1.0,
    val increment: Double = 0.0) {

  override fun toString(): String = symbol
}

class DU_COMBINED(q: UNKNOWN) : DerivedUnit<UNKNOWN>("", q.toString(), SI_COMBINED(q))

object DU_UNITLESS : DerivedUnit<Q_UNITLESS>("", "", SI_UNITLESS)
object DU_MOLE : DerivedUnit<AMOUNT>("Mole", "m", SI_MOLE)
object DU_AMPERE : DerivedUnit<ELECTRIC_CURRENT>("Ampere", "A", SI_AMPERE)
object DU_METER : DerivedUnit<LENGTH>("Meter", "m", SI_METER)
object DU_SQUARE_METER : DerivedUnit<AREA>("Square Meter", "m²", SI_AREA)
object DU_CUBIC_METER : DerivedUnit<VOLUME>("Cubic Meter", "m³", SI_VOLUME)
object DU_CANDELA : DerivedUnit<LUMINOUS_INTENSITY>("Candela", "cd", SI_CANDELA)
object DU_GRAM : DerivedUnit<MASS>("Gram", "g", SI_GRAM)
object DU_KELVIN : DerivedUnit<THERMODYNAMIC_TEMPERATURE>("Kelvin", "K", SI_KELVIN)
object DU_SECOND : DerivedUnit<TIME>("Second", "s", SI_SECOND)
object DU_RADIAN : DerivedUnit<ANGLE>("Radian", "rad", SI_RADIAN)
object DU_STERADIAN : DerivedUnit<ANGLE>("Steradian", "sr", SI_STERADIAN)
object DU_HERTZ : DerivedUnit<FREQUENCY>("Hertz", "Hz", SI_HERTZ)
object DU_NEWTON : DerivedUnit<FORCE>("Newton", "N", SI_NEWTON)
object DU_PASCAL : DerivedUnit<PRESSURE>("Pascal", "PA", SI_PASCAL)
object DU_JOULE : DerivedUnit<WORK>("Joule", "J", SI_JOULE)
object DU_WATT : DerivedUnit<POWER>("Watt", "W", SI_WATT)
