package de.hdc.measure

import ch.obermuhlner.math.big.*
import ch.obermuhlner.math.big.BigFloat.*

enum class NumberName(val longName: String, val multiplier: BigFloat) {
  TRIACONTILLIONTH("triacontillionth", context(1).valueOf(1e-90)),
  ICOSIENNILLIONTH("icosiennillionth", context(1).valueOf(1e-87)),
  ICOSIOKTILLIONTH("icosioktillionth", context(1).valueOf(1e-84)),
  ICOSIHEPTILLIONTH("icosiheptillionth", context(1).valueOf(1e-81)),
  ICOSIHEXILLIONTH("icosihexillionth", context(1).valueOf(1e-78)),
  ICOSIPENTILLIONTH("icosipentillionth", context(1).valueOf(1e-75)),
  ICOSITETRILLIONTH("icositetrillionth", context(1).valueOf(1e-72)),
  ICOSITRILLIONTH("icositrillionth", context(1).valueOf(1e-69)),
  ICOSIDILLIONTH("icosidillionth", context(1).valueOf(1e-66)),
  ICOSIHENILLIONTH("icosihenillionth", context(1).valueOf(1e-63)),
  ICOSILLIONTH("icosillionth", context(1).valueOf(1e-60)),
  ENNEADEKILLIONTH("enneadekillionth", context(1).valueOf(1e-57)),
  OKTADEKILLIONTH("oktadekillionth", context(1).valueOf(1e-54)),
  HEPTADEKILLIONTH("heptadekillionth", context(1).valueOf(1e-51)),
  HEXADEKILLIONTH("hexadekillionth", context(1).valueOf(1e-48)),
  PENTADEKILLIONTH("pentadekillionth", context(1).valueOf(1e-45)),
  TETRADEKILLIONTH("tetradekillionth", context(1).valueOf(1e-42)),
  TRISDEKILLIONTH("trisdekillionth", context(1).valueOf(1e-39)),
  DODEKILLIONTH("dodekillionth", context(1).valueOf(1e-36)),
  HENDEKILLIONTH("hendekillionth", context(1).valueOf(1e-33)),
  DEKILLIONTH("dekillionth", context(1).valueOf(1e-30)),
  ENNILLIONTH("ennillionth", context(1).valueOf(1e-27)),
  OKTILLIONTH("oktillionth", context(1).valueOf(1e-24)),
  TETRILLIONTH("tetrillionth", context(1).valueOf(1e-12)),
  HEPTILLIONTH("heptillionth", context(1).valueOf(1e-21)),
  HEXILLIONTH("hexillionth", context(1).valueOf(1e-18)),
  PENTILLIONTH("pentillionth", context(1).valueOf(1e-15)),
  GILLIONTH("gillionth", context(1).valueOf(1e-9)),
  MILLIONTH("millionth", context(1).valueOf(1e-6)),
  THOUSANDTH("thousandth", context(1).valueOf(1e-3)),
  NONE("", context(1).valueOf(1.0)),
  THOUSAND("thousand", context(1).valueOf(1e3)),
  MILLION("million", context(1).valueOf(1e6)),
  GILLION("gillion", context(1).valueOf(1e9)),
  TETRILLION("tetrillion", context(1).valueOf(1e12)),
  PENTILLION("pentillion", context(1).valueOf(1e15)),
  HEXILLION("hexillion", context(1).valueOf(1e18)),
  HEPTILLION("heptillion", context(1).valueOf(1e21)),
  OKTILLION("oktillion", context(1).valueOf(1e24)),
  ENNILLION("ennillion", context(1).valueOf(1e27)),
  DEKILLION("dekillion", context(1).valueOf(1e30)),
  HENDEKILLION("hendekillion", context(1).valueOf(1e33)),
  DODEKILLION("dodekillion", context(1).valueOf(1e36)),
  TRISDEKILLION("trisdekillion", context(1).valueOf(1e39)),
  TETRADEKILLION("tetradekillion", context(1).valueOf(1e42)),
  PENTADEKILLION("pentadekillion", context(1).valueOf(1e45)),
  HEXADEKILLION("hexadekillion", context(1).valueOf(1e48)),
  HEPTADEKILLION("heptadekillion", context(1).valueOf(1e51)),
  OKTADEKILLION("oktadekillion", context(1).valueOf(1e54)),
  ENNEADEKILLION("enneadekillion", context(1).valueOf(1e57)),
  ICOSILLION("icosillion", context(1).valueOf(1e60)),
  ICOSIHENILLION("icosihenillion", context(1).valueOf(1e63)),
  ICOSIDILLION("icosidillion", context(1).valueOf(1e66)),
  ICOSITRILLION("icositrillion", context(1).valueOf(1e69)),
  ICOSITETRILLION("icositetrillion", context(1).valueOf(1e72)),
  ICOSIPENTILLION("icosipentillion", context(1).valueOf(1e75)),
  ICOSIHEXILLION("icosihexillion", context(1).valueOf(1e78)),
  ICOSIHEPTILLION("icosiheptillion", context(1).valueOf(1e81)),
  ICOSIOKTILLION("icosioktillion", context(1).valueOf(1e84)),
  ICOSIENNILLION("icosiennillion", context(1).valueOf(1e87)),
  TRIACONTILLION("triacontillion", context(1).valueOf(1e90));

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
  NONE("", "", context(1).valueOf(1)),
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

enum class Prefix(val longName: String, val symbol: String, val multiplier: BigFloat) {
  y("yocto", "y", context(1).valueOf(1e-24)),
  z("zepto", "z", context(1).valueOf(1e-21)),
  a("atto", "a", context(1).valueOf(1e-18)),
  f("femto", "f", context(1).valueOf(1e-15)),
  p("pico", "p", context(1).valueOf(1e-12)),
  n("nano", "n", context(1).valueOf(1e-9)),
  µ("micro", "µ", context(1).valueOf(1e-6)),
  m("milli", "m", context(1).valueOf(1e-3)),
  c("centi", "c", context(1).valueOf(1e-2)),
  d("deci", "d", context(1).valueOf(1e-1)),
  NONE("", "", context(1).valueOf(1e0)),
  da("deca", "da", context(1).valueOf(1e1)),
  h("hecto", "h", context(1).valueOf(1e2)),
  k("kilo", "k", context(1).valueOf(1e3)),
  M("mega", "M", context(1).valueOf(1e6)),
  G("giga", "G", context(1).valueOf(1e9)),
  T("tera", "T", context(1).valueOf(1e12)),
  P("peta", "P", context(1).valueOf(1e15)),
  X("exa", "X", context(1).valueOf(1e18)),
  Z("zeta", "Z", context(1).valueOf(1e21)),
  Y("yota", "Y", context(1).valueOf(1e24));

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

infix fun <T: BaseUnit> Number.Y(measureUnit: MeasureUnit<T>): Measure<T> {
  return Measure(this.toDouble(),  Prefix.Y, measureUnit)
}
infix fun <T: BaseUnit> Number.Z(measureUnit: MeasureUnit<T>): Measure<T> {
  return Measure(this.toDouble(),  Prefix.Z, measureUnit)
}
infix fun <T: BaseUnit> Number.X(measureUnit: MeasureUnit<T>): Measure<T> {
  return Measure(this.toDouble(),  Prefix.X, measureUnit)
}
infix fun <T: BaseUnit> Number.P(measureUnit: MeasureUnit<T>): Measure<T> {
  return Measure(this.toDouble(),  Prefix.P, measureUnit)
}
infix fun <T: BaseUnit> Number.T(measureUnit: MeasureUnit<T>): Measure<T> {
  return Measure(this.toDouble(),  Prefix.T, measureUnit)
}
infix fun <T: BaseUnit> Number.G(measureUnit: MeasureUnit<T>): Measure<T> {
  return Measure(this.toDouble(),  Prefix.G, measureUnit)
}
infix fun <T: BaseUnit> Number.M(measureUnit: MeasureUnit<T>): Measure<T> {
  return Measure(this.toDouble(),  Prefix.M, measureUnit)
}
infix fun <T: BaseUnit> Number.k(measureUnit: MeasureUnit<T>): Measure<T> {
  return Measure(this.toDouble(),  Prefix.k, measureUnit)
}

infix fun <T: BaseUnit> Number.h(measureUnit: MeasureUnit<T>): Measure<T> {
  return Measure(this.toDouble(),  Prefix.h, measureUnit)
}
infix fun <T: BaseUnit> Number.da(measureUnit: MeasureUnit<T>): Measure<T> {
  return Measure(this.toDouble(),  Prefix.da, measureUnit)
}

infix fun <T: BaseUnit> Number.ˍ(measureUnit: MeasureUnit<T>): Measure<T> {
  return Measure(this.toDouble(),  Prefix.NONE, measureUnit)
}
infix fun <T: BaseUnit> Number.d(measureUnit: MeasureUnit<T>): Measure<T> {
  return Measure(this.toDouble(),  Prefix.d, measureUnit)
}

infix fun <T: BaseUnit> Number.c(measureUnit: MeasureUnit<T>): Measure<T> {
  return Measure(this.toDouble(),  Prefix.c, measureUnit)
}

infix fun <T: BaseUnit> Number.m(measureUnit: MeasureUnit<T>): Measure<T> {
  return Measure(this.toDouble(),  Prefix.m, measureUnit)
}
infix fun <T: BaseUnit> Number.µ(measureUnit: MeasureUnit<T>): Measure<T> {
  return Measure(this.toDouble(),  Prefix.µ, measureUnit)
}
infix fun <T: BaseUnit> Number.n(measureUnit: MeasureUnit<T>): Measure<T> {
  return Measure(this.toDouble(),  Prefix.n, measureUnit)
}
infix fun <T: BaseUnit> Number.p(measureUnit: MeasureUnit<T>): Measure<T> {
  return Measure(this.toDouble(),  Prefix.p, measureUnit)
}
infix fun <T: BaseUnit> Number.f(measureUnit: MeasureUnit<T>): Measure<T> {
  return Measure(this.toDouble(),  Prefix.f, measureUnit)
}
infix fun <T: BaseUnit> Number.a(measureUnit: MeasureUnit<T>): Measure<T> {
  return Measure(this.toDouble(),  Prefix.a, measureUnit)
}
infix fun <T: BaseUnit> Number.z(measureUnit: MeasureUnit<T>): Measure<T> {
  return Measure(this.toDouble(),  Prefix.z, measureUnit)
}
infix fun <T: BaseUnit> Number.y(measureUnit: MeasureUnit<T>): Measure<T> {
  return Measure(this.toDouble(),  Prefix.y, measureUnit)
}

