package de.hdc.measure

//enum class BaseQuantity {
//  AMOUNT, ELECTRIC_CURRENT, LENGTH, LUMINOUS_INTENSITY, MASS, THERMODYNAMIC_TEMPERATURE, TIME
//}

data class Quantity(
        val mol: Int = 0,
        val A: Int = 0,
        val m: Int = 0,
        val Cd: Int = 0,
        val g: Int = 0,
        val K: Int = 0,
        val s: Int = 0) {

  override fun toString(): String {
    var st = ""
    if (mol == 1) st += "mol "
    if (mol > 1) st += "mol^$mol "
    if (A == 1) st += "A "
    if (A > 1) st += "A^$A "
    if (m == 1) st += "m "
    if (m > 1) st += "m^$m "
    if (Cd == 1) st += "cd "
    if (Cd > 1) st += "cd^$Cd "
    if (g == 1) st += "g "
    if (g > 1) st += "g^$g "
    if (K == 1) st += "K "
    if (K > 1) st += "K^$K "
    if (s == 1) st += "s "
    if (s > 1) st += "s^${this.s} "
    st = st.trim()

    var s2 = ""
    if (mol == -1) s2 += "mol "
    if (mol < -1) s2 += "mol^${Math.abs(mol)} "
    if (A == -1) s2 += "A "
    if (A < -1) s2 += "A^${Math.abs(A)} "
    if (m == -1) s2 += "m "
    if (m < -1) s2 += "m^${Math.abs(m)} "
    if (Cd == -1) s2 += "cd "
    if (Cd < -1) s2 += "cd^${Math.abs(Cd)} "
    if (g == -1) s2 += "g "
    if (g < -1) s2 += "g^${Math.abs(g)} "
    if (K == -1) s2 += "K "
    if (K < -1) s2 += "K^${Math.abs(K)} "
    if (s == -1) s2 += "s "
    if (s < -1) s2 += "s^${Math.abs(this.s)} "
    s2 = s2.trim()

    if (st.isEmpty() && s2.isNotEmpty()) {
        st += "1"
    }
    if (s2.isNotEmpty()) {
        st += "/$s2"
    }

    return st.replace(' ', '·')
  }

  override fun equals(other: Any?): Boolean {
    if (other !is Quantity) {
      return false
    }
    return ((mol == other.mol)
        && (A == other.A)
        && (m == other.m)
        && (Cd == other.Cd)
        && (g == other.g)
        && (K == other.K)
        && (s == other.s)
        )
  }

  override fun hashCode(): Int {
    return super.hashCode()
  }

  operator fun times(q: Quantity): Quantity {
    return Quantity(mol + q.mol
            , A + q.A
            , m + q.m
            , Cd + q.Cd
            , g + q.g
            , K + q.K
            , s + q.s)
  }

  operator fun div(q: Quantity): Quantity {
    return Quantity(mol - q.mol
            , A - q.A
            , m - q.m
            , Cd - q.Cd
            , g - q.g
            , K - q.K
            , s - q.s)
  }
}
