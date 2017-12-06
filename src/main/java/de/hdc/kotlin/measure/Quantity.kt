package de.hdc.kotlin.measure

enum class BaseQuantity {
  AMOUNT, ELECTRIC_CURRENT, LENGTH, LUMINOUS_INTENSITY, MASS, THERMODYNAMIC_TEMPERATURE, TIME
}

data class Quantity(
    val amount: Int = 0,
    val electricCurrent: Int = 0,
    val length: Int = 0,
    val luminousIntensity: Int = 0,
    val mass: Int = 0,
    val thermodynamicTemperature: Int = 0,
    val time: Int = 0) {

  override fun toString(): String {
    var s = ""
    when {
      amount == 1 -> s += "mol "
      amount > 1 -> s += "mol^$amount "
    }
    when {
      electricCurrent == 1 -> s += "A "
      electricCurrent > 1 -> s += "A^$electricCurrent "
    }
    when {
      length == 1 -> s += "m "
      length > 1 -> s += "m^$length "
    }
    when {
      luminousIntensity == 1 -> s += "cd "
      luminousIntensity > 1 -> s += "cd^$luminousIntensity "
    }
    when {
      mass == 1 -> s += "g "
      mass > 1 -> s += "g^$mass "
    }
    when {
      thermodynamicTemperature == 1 -> s += "K "
      thermodynamicTemperature > 1 -> s += "K^$thermodynamicTemperature "
    }
    when {
      time == 1 -> s += "s "
      time > 1 -> s += "s^$time "
    }
    s = s.trim()

    var s2 = ""
    when {
      amount == -1 -> s2 += "mol "
      amount < -1 -> s2 += "mol^${Math.abs(amount)} "
    }
    when {
      electricCurrent == -1 -> s2 += "A "
      electricCurrent < -1 -> s2 += "A^${Math.abs(electricCurrent)} "
    }
    when {
      length == -1 -> s2 += "m "
      length < -1 -> s2 += "m^${Math.abs(length)} "
    }
    when {
      luminousIntensity == -1 -> s2 += "cd "
      luminousIntensity < -1 -> s2 += "cd^${Math.abs(luminousIntensity)} "
    }
    when {
      mass == -1 -> s2 += "g "
      mass < -1 -> s2 += "g^${Math.abs(mass)} "
    }
    when {
      thermodynamicTemperature == -1 -> s2 += "K "
      thermodynamicTemperature < -1 -> s2 += "K^${Math.abs(thermodynamicTemperature)} "
    }
    when {
      time == -1 -> s2 += "s "
      time < -1 -> s2 += "s^${Math.abs(time)} "
    }

    s2 = s2.trim()

    if (s.isEmpty() && s2.isNotEmpty()) {
        s += "1"
    }
    if (s2.isNotEmpty()) {
        s += "/" + s2
    }

    return s.replace(' ', '·')
  }

  override fun equals(other: Any?): Boolean {
    if (other !is Quantity) {
      return false
    }
    return ((amount == other.amount)
        && (electricCurrent == other.electricCurrent)
        && (length == other.length)
        && (luminousIntensity == other.luminousIntensity)
        && (mass == other.mass)
        && (thermodynamicTemperature == other.thermodynamicTemperature)
        && (time == other.time)
        )
  }

  override fun hashCode(): Int {
    return super.hashCode()
  }

  fun times(q: Quantity): Quantity {
    return Quantity(amount + q.amount
        , electricCurrent + q.electricCurrent
        , length + q.length
        , luminousIntensity + q.luminousIntensity
        , mass + q.mass
        , thermodynamicTemperature + q.thermodynamicTemperature
        , time + q.time)
  }

  fun div(q: Quantity): Quantity {
    return Quantity(amount - q.amount
        , electricCurrent - q.electricCurrent
        , length - q.length
        , luminousIntensity - q.luminousIntensity
        , mass - q.mass
        , thermodynamicTemperature - q.thermodynamicTemperature
        , time - q.time)
  }
}
