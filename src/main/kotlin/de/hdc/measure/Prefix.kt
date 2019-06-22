package de.hdc.measure

import ch.obermuhlner.math.big.*
import ch.obermuhlner.math.big.BigFloat.*

enum class NumberName(val longName: String, val multiplier: BigFloat) {
  TRIACONTILLIONTH("triacontillionth", con.valueOf(1e-90)),
  ICOSIENNILLIONTH("icosiennillionth", con.valueOf(1e-87)),
  ICOSIOKTILLIONTH("icosioktillionth", con.valueOf(1e-84)),
  ICOSIHEPTILLIONTH("icosiheptillionth", con.valueOf(1e-81)),
  ICOSIHEXILLIONTH("icosihexillionth", con.valueOf(1e-78)),
  ICOSIPENTILLIONTH("icosipentillionth", con.valueOf(1e-75)),
  ICOSITETRILLIONTH("icositetrillionth", con.valueOf(1e-72)),
  ICOSITRILLIONTH("icositrillionth", con.valueOf(1e-69)),
  ICOSIDILLIONTH("icosidillionth", con.valueOf(1e-66)),
  ICOSIHENILLIONTH("icosihenillionth", con.valueOf(1e-63)),
  ICOSILLIONTH("icosillionth", con.valueOf(1e-60)),
  ENNEADEKILLIONTH("enneadekillionth", con.valueOf(1e-57)),
  OKTADEKILLIONTH("oktadekillionth", con.valueOf(1e-54)),
  HEPTADEKILLIONTH("heptadekillionth", con.valueOf(1e-51)),
  HEXADEKILLIONTH("hexadekillionth", con.valueOf(1e-48)),
  PENTADEKILLIONTH("pentadekillionth", con.valueOf(1e-45)),
  TETRADEKILLIONTH("tetradekillionth", con.valueOf(1e-42)),
  TRISDEKILLIONTH("trisdekillionth", con.valueOf(1e-39)),
  DODEKILLIONTH("dodekillionth", con.valueOf(1e-36)),
  HENDEKILLIONTH("hendekillionth", con.valueOf(1e-33)),
  DEKILLIONTH("dekillionth", con.valueOf(1e-30)),
  ENNILLIONTH("ennillionth", con.valueOf(1e-27)),
  OKTILLIONTH("oktillionth", con.valueOf(1e-24)),
  TETRILLIONTH("tetrillionth", con.valueOf(1e-12)),
  HEPTILLIONTH("heptillionth", con.valueOf(1e-21)),
  HEXILLIONTH("hexillionth", con.valueOf(1e-18)),
  PENTILLIONTH("pentillionth", con.valueOf(1e-15)),
  GILLIONTH("gillionth", con.valueOf(1e-9)),
  MILLIONTH("millionth", con.valueOf(1e-6)),
  THOUSANDTH("thousandth", con.valueOf(1e-3)),
  NONE("", con.valueOf(1.0)),
  THOUSAND("thousand", con.valueOf(1e3)),
  MILLION("million", con.valueOf(1e6)),
  GILLION("gillion", con.valueOf(1e9)),
  TETRILLION("tetrillion", con.valueOf(1e12)),
  PENTILLION("pentillion", con.valueOf(1e15)),
  HEXILLION("hexillion", con.valueOf(1e18)),
  HEPTILLION("heptillion", con.valueOf(1e21)),
  OKTILLION("oktillion", con.valueOf(1e24)),
  ENNILLION("ennillion", con.valueOf(1e27)),
  DEKILLION("dekillion", con.valueOf(1e30)),
  HENDEKILLION("hendekillion", con.valueOf(1e33)),
  DODEKILLION("dodekillion", con.valueOf(1e36)),
  TRISDEKILLION("trisdekillion", con.valueOf(1e39)),
  TETRADEKILLION("tetradekillion", con.valueOf(1e42)),
  PENTADEKILLION("pentadekillion", con.valueOf(1e45)),
  HEXADEKILLION("hexadekillion", con.valueOf(1e48)),
  HEPTADEKILLION("heptadekillion", con.valueOf(1e51)),
  OKTADEKILLION("oktadekillion", con.valueOf(1e54)),
  ENNEADEKILLION("enneadekillion", con.valueOf(1e57)),
  ICOSILLION("icosillion", con.valueOf(1e60)),
  ICOSIHENILLION("icosihenillion", con.valueOf(1e63)),
  ICOSIDILLION("icosidillion", con.valueOf(1e66)),
  ICOSITRILLION("icositrillion", con.valueOf(1e69)),
  ICOSITETRILLION("icositetrillion", con.valueOf(1e72)),
  ICOSIPENTILLION("icosipentillion", con.valueOf(1e75)),
  ICOSIHEXILLION("icosihexillion", con.valueOf(1e78)),
  ICOSIHEPTILLION("icosiheptillion", con.valueOf(1e81)),
  ICOSIOKTILLION("icosioktillion", con.valueOf(1e84)),
  ICOSIENNILLION("icosiennillion", con.valueOf(1e87)),
  TRIACONTILLION("triacontillion", con.valueOf(1e90));

  override fun toString(): String {
    return longName
  }

  fun isLast(): Boolean {
    return ((this == THOUSAND) || (this == TRIACONTILLION))
  }

  fun up(): NumberName {
    return when {
      isLast() -> this
      else -> NumberName.values()[ordinal + 1]
    }
  }

  fun down(): NumberName {
    return when {
      isLast() -> this
      else -> NumberName.values()[ordinal - 1]
    }
  }
}

enum class BinaryPrefix(val longName: String, val symbol: String, val multiplier: BigFloat) {
  NONE("", "", con.valueOf(1)),
  KIBI("kibi", "Ki", context(4).valueOf(1024e1)),
  MEBI("mebi", "Mi", context(8).valueOf(1024e2)),
  GIBI("gibi", "Gi", context(12).valueOf(1024e3)),
  TEBI("tebi", "Ti", context(16).valueOf(1024e4)),
  PEBI("pebi", "Pi", context(20).valueOf(1024e5)),
  EXBI("exbi", "Xi", context(24).valueOf(1024e6)),
  ZEBI("zebi", "Zi", context(28).valueOf(1024e7)),
  YOBI("yobi", "Yi", context(32).valueOf(1024e8));

  override fun toString(): String {
    return symbol
  }

  fun isLast(): Boolean {
    return ((this == NONE) || (this == YOBI))
  }

  fun up(): BinaryPrefix {
    return when {
      isLast() -> this
      else -> BinaryPrefix.values()[ordinal + 1]
    }
  }

  fun down(): BinaryPrefix {
    return when {
      isLast() -> this
      else -> BinaryPrefix.values()[ordinal - 1]
    }
  }
}

private val con: Context = context(1)

enum class Prefix(val longName: String, val symbol: String, val multiplier: BigFloat) {
  y("yocto", "y", con.valueOf(1e-24)),
  z("zepto", "z", con.valueOf(1e-21)),
  a("atto", "a", con.valueOf(1e-18)),
  f("femto", "f", con.valueOf(1e-15)),
  p("pico", "p", con.valueOf(1e-12)),
  n("nano", "n", con.valueOf(1e-9)),
  µ("micro", "µ", con.valueOf(1e-6)),
  m("milli", "m", con.valueOf(1e-3)),
  c("centi", "con", con.valueOf(1e-2)),
  d("deci", "d", con.valueOf(1e-1)),
  NONE("", "", con.valueOf(1e0)),
  da("deca", "da", con.valueOf(1e1)),
  h("hecto", "h", con.valueOf(1e2)),
  k("kilo", "k", con.valueOf(1e3)),
  M("mega", "M", con.valueOf(1e6)),
  G("giga", "G", con.valueOf(1e9)),
  T("tera", "T", con.valueOf(1e12)),
  P("peta", "P", con.valueOf(1e15)),
  X("exa", "X", con.valueOf(1e18)),
  Z("zeta", "Z", con.valueOf(1e21)),
  Y("yota", "Y", con.valueOf(1e24));

  override fun toString(): String {
    return symbol
  }

  fun isLast(): Boolean {
    return ((this == Y) || (this == y))
  }

  fun up(): Prefix {
    return when {
      isLast() -> this
      this == h -> k
      this == da -> k
      this == NONE -> k
      this == d -> NONE
      this == c -> NONE
      this == m -> NONE
      else -> Prefix.values()[ordinal + 1]
    }
  }

  fun down(): Prefix {
    return when {
      isLast() -> this
      this == k -> NONE
      this == h -> NONE
      this == da -> NONE
      this == NONE -> m
      this == d -> m
      this == c -> m
      else -> Prefix.values()[ordinal - 1]
    }
  }
}

infix fun <T : BaseUnit> Number.Y(measureUnit: MeasureUnit<T>): Measure<T> {
  return Measure(this.toDouble(), Prefix.Y, measureUnit)
}

infix fun <T : BaseUnit> Number.Z(measureUnit: MeasureUnit<T>): Measure<T> {
  return Measure(this.toDouble(), Prefix.Z, measureUnit)
}

infix fun <T : BaseUnit> Number.X(measureUnit: MeasureUnit<T>): Measure<T> {
  return Measure(this.toDouble(), Prefix.X, measureUnit)
}

infix fun <T : BaseUnit> Number.P(measureUnit: MeasureUnit<T>): Measure<T> {
  return Measure(this.toDouble(), Prefix.P, measureUnit)
}

infix fun <T : BaseUnit> Number.T(measureUnit: MeasureUnit<T>): Measure<T> {
  return Measure(this.toDouble(), Prefix.T, measureUnit)
}

infix fun <T : BaseUnit> Number.G(measureUnit: MeasureUnit<T>): Measure<T> {
  return Measure(this.toDouble(), Prefix.G, measureUnit)
}

infix fun <T : BaseUnit> Number.M(measureUnit: MeasureUnit<T>): Measure<T> {
  return Measure(this.toDouble(), Prefix.M, measureUnit)
}

infix fun <T : BaseUnit> Number.k(measureUnit: MeasureUnit<T>): Measure<T> {
  return Measure(this.toDouble(), Prefix.k, measureUnit)
}

infix fun <T : BaseUnit> Number.h(measureUnit: MeasureUnit<T>): Measure<T> {
  return Measure(this.toDouble(), Prefix.h, measureUnit)
}

infix fun <T : BaseUnit> Number.da(measureUnit: MeasureUnit<T>): Measure<T> {
  return Measure(this.toDouble(), Prefix.da, measureUnit)
}

infix fun <T : BaseUnit> Number.ˍ(measureUnit: MeasureUnit<T>): Measure<T> {
  return Measure(this.toDouble(), Prefix.NONE, measureUnit)
}

infix fun <T : BaseUnit> Number.d(measureUnit: MeasureUnit<T>): Measure<T> {
  return Measure(this.toDouble(), Prefix.d, measureUnit)
}

infix fun <T : BaseUnit> Number.c(measureUnit: MeasureUnit<T>): Measure<T> {
  return Measure(this.toDouble(), Prefix.c, measureUnit)
}

infix fun <T : BaseUnit> Number.m(measureUnit: MeasureUnit<T>): Measure<T> {
  return Measure(this.toDouble(), Prefix.m, measureUnit)
}

infix fun <T : BaseUnit> Number.µ(measureUnit: MeasureUnit<T>): Measure<T> {
  return Measure(this.toDouble(), Prefix.µ, measureUnit)
}

infix fun <T : BaseUnit> Number.n(measureUnit: MeasureUnit<T>): Measure<T> {
  return Measure(this.toDouble(), Prefix.n, measureUnit)
}

infix fun <T : BaseUnit> Number.p(measureUnit: MeasureUnit<T>): Measure<T> {
  return Measure(this.toDouble(), Prefix.p, measureUnit)
}

infix fun <T : BaseUnit> Number.f(measureUnit: MeasureUnit<T>): Measure<T> {
  return Measure(this.toDouble(), Prefix.f, measureUnit)
}

infix fun <T : BaseUnit> Number.a(measureUnit: MeasureUnit<T>): Measure<T> {
  return Measure(this.toDouble(), Prefix.a, measureUnit)
}

infix fun <T : BaseUnit> Number.z(measureUnit: MeasureUnit<T>): Measure<T> {
  return Measure(this.toDouble(), Prefix.z, measureUnit)
}

infix fun <T : BaseUnit> Number.y(measureUnit: MeasureUnit<T>): Measure<T> {
  return Measure(this.toDouble(), Prefix.y, measureUnit)
}

