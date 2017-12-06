package de.hdc.kotlin.measure

sealed class SIUnit(val quantity: Quantity)

// SI Units
class SI_COMBINED(q: Quantity) : SIUnit(q)

object SI_UNITLESS : SIUnit(Quantity())
object SI_MOLE : SIUnit(Quantity(amount = 1))
object SI_AMPERE : SIUnit(Quantity(electricCurrent = 1))
object SI_METER : SIUnit(Quantity(length = 1))
object SI_CANDELA : SIUnit(Quantity(luminousIntensity = 1))
object SI_GRAM : SIUnit(Quantity(mass = 1))
object SI_KELVIN : SIUnit(Quantity(thermodynamicTemperature = 1))
object SI_SECOND : SIUnit(Quantity(time = 1))
// Derived SI Units
object SI_AREA : SIUnit(Quantity(length = 2))
object SI_VOLUME : SIUnit(Quantity(length = 3))
object SI_RADIAN : SIUnit(Quantity())
object SI_STERADIAN : SIUnit(Quantity())
object SI_HERTZ : SIUnit(Quantity(time = -1))
object SI_NEWTON : SIUnit(Quantity(length = 1, mass = 1, time = -2))
object SI_PASCAL : SIUnit(Quantity(length = -1, mass = 1, time = -2))
object SI_JOULE : SIUnit(Quantity(length = 2, mass = 1, time = -2))
object SI_WATT : SIUnit(Quantity(length = 2, mass = 1, time = -3))
object SI_VELOCITY : SIUnit(Quantity(length = 1, mass = 0, time = -1))
object SI_ACCELERATION : SIUnit(Quantity(length = 1, time = -2))
//...

open class DerivedUnit<out T : SIUnit>(
    val longName: String,
    val symbol: String,
    val baseUnit: T,
    val multiplier: Double = 1.0,
    val increment: Double = 0.0) {
  override fun toString(): String {
    return symbol
  }
}

class DU_COMBINED(q: Quantity) : DerivedUnit<SI_COMBINED>("", q.toString(), SI_COMBINED(q))
object DU_UNITLESS : DerivedUnit<SI_UNITLESS>("", "", SI_UNITLESS)
object DU_MOLE : DerivedUnit<SI_MOLE>("Mole", "m", SI_MOLE)
object DU_AMPERE : DerivedUnit<SI_AMPERE>("Ampere", "A", SI_AMPERE)
object DU_METER : DerivedUnit<SI_METER>("Meter", "m", SI_METER)
object DU_SQUARE_METER : DerivedUnit<SI_AREA>("Square Meter", "m²", SI_AREA)
object DU_CUBIC_METER : DerivedUnit<SI_VOLUME>("Cubic Meter", "m³", SI_VOLUME)
object DU_CANDELA : DerivedUnit<SI_CANDELA>("Candela", "cd", SI_CANDELA)
object DU_GRAM : DerivedUnit<SI_GRAM>("Gram", "g", SI_GRAM)
object DU_KELVIN : DerivedUnit<SI_KELVIN>("Kelvin", "K", SI_KELVIN)
object DU_SECOND : DerivedUnit<SI_SECOND>("Second", "s", SI_SECOND)
object DU_RADIAN : DerivedUnit<SI_RADIAN>("Radian", "rad", SI_RADIAN)
object DU_STERADIAN : DerivedUnit<SI_STERADIAN>("Steradian", "sr", SI_STERADIAN)
object DU_HERTZ : DerivedUnit<SI_HERTZ>("Hertz", "Hz", SI_HERTZ)
object DU_NEWTON : DerivedUnit<SI_NEWTON>("Newton", "N", SI_NEWTON)
object DU_PASCAL : DerivedUnit<SI_PASCAL>("Pascal", "PA", SI_PASCAL)
object DU_JOULE : DerivedUnit<SI_JOULE>("Joule", "J", SI_JOULE)
object DU_WATT : DerivedUnit<SI_WATT>("Watt", "W", SI_WATT)
