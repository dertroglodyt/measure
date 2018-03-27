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

  override fun equals(other: Any?): Boolean = (other is DerivedUnit<*>)
      && (baseUnit.quantity == other.baseUnit.quantity) && (multiplier == other.multiplier) && (increment == other.increment)

  override fun hashCode(): Int = super.hashCode()

}
