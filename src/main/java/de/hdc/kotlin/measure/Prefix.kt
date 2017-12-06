package de.hdc.kotlin.measure

enum class Prefix(val longName: String, val symbol: Char, val multiplier: Double) {
  YOTA("Yota", 'Y', 1e24),
  ZETA("Zeta", 'Z', 1e21),
  EXA("Exa", 'X', 1e18),
  PETA("Peta", 'P', 1e15),
  TERA("Tera", 'T', 1e12),
  GIGA("giga", 'G', 1e9),
  MEGA("mega", 'M', 1e6),
  KILO("kilo", 'k', 1e3),
// Messes up pretty formatting
//  HECTO("hecto", "h", 1e2),
//  DECA("deca", "da", 1e1),
  NONE("", ' ', 1e0),
// Messes up pretty formatting
//  DECI("deci", "d", 1e-1),
//  CENTI("centi", "c", 1e-2),
  MILLI("milli", 'm', 1e-3),
  MICRO("micro", 'µ', 1e-6),
  NANO("nano", 'n', 1e-9),
  PICO("pico", 'p', 1e-12),
  FEMTO("femto", 'f', 1e-15),
  ATTO("atto", 'a', 1e-18),
  ZEPTO("zepto", 'z', 1e-21),
  YOCTO("yocto", 'y', 1e-24)
  ;

  override fun toString(): String {
    return if (symbol == ' ') "" else symbol+""
  }

  fun isLast(): Boolean {
    return ((this == YOTA) || (this == YOCTO))
  }

  fun up(): Prefix {
    if (isLast()) {
      return this
    }
    return Prefix.values()[ordinal + 1]
  }

  fun down(): Prefix {
    if (isLast()) {
      return this
    }
    return Prefix.values()[ordinal - 1]
  }
}
