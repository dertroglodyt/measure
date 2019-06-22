package de.hdc.measure

sealed class BaseUnit(val quantity: Quantity) {
  override fun toString(): String {
    return "BaseUnit(quantity=$quantity)"
  }
}

// SI Units
class SI_COMBINED(q: Quantity) : BaseUnit(q)

class SI_FLUX(q: Quantity) : BaseUnit(q)

object SI_UNITLESS : BaseUnit(Quantity())
object SI_MOLE : BaseUnit(Quantity(mol = 1))
object SI_AMPERE : BaseUnit(Quantity(A = 1))
object SI_METER : BaseUnit(Quantity(m = 1))
object SI_CANDELA : BaseUnit(Quantity(Cd = 1))
object SI_KILOGRAM : BaseUnit(Quantity(kg = 1))
object SI_KELVIN : BaseUnit(Quantity(K = 1))
object SI_SECOND : BaseUnit(Quantity(s = 1))
// Derived SI Units
object SI_PCS : BaseUnit(Quantity())
object SI_AREA : BaseUnit(Quantity(m = 2))
object SI_VOLUME : BaseUnit(Quantity(m = 3))
object SI_RADIAN : BaseUnit(Quantity())
object SI_STERADIAN : BaseUnit(Quantity())
object SI_HERTZ : BaseUnit(Quantity(s = -1))
object SI_NEWTON : BaseUnit(Quantity(m = 1, kg = 1, s = -2))
object SI_PASCAL : BaseUnit(Quantity(m = -1, kg = 1, s = -2))
object SI_JOULE : BaseUnit(Quantity(m = 2, kg = 1, s = -2))
object SI_WATT : BaseUnit(Quantity(m = 2, kg = 1, s = -3))
object SI_VELOCITY : BaseUnit(Quantity(m = 1, s = -1))
object SI_ACCELERATION : BaseUnit(Quantity(m = 1, s = -2))
object SI_ANGULAR_VELOCITY : BaseUnit(Quantity(s = -1))
