package de.hdc.measure

import java.math.BigInteger

enum class NumberName(val longName: String, val multiplier: Double) {
  TRIACONTILLIONTH("triacontillionth", 1e-90),
  ICOSIENNILLIONTH("icosiennillionth", 1e-87),
  ICOSIOKTILLIONTH("icosioktillionth", 1e-84),
  ICOSIHEPTILLIONTH("icosiheptillionth", 1e-81),
  ICOSIHEXILLIONTH("icosihexillionth", 1e-78),
  ICOSIPENTILLIONTH("icosipentillionth", 1e-75),
  ICOSITETRILLIONTH("icositetrillionth", 1e-72),
  ICOSITRILLIONTH("icositrillionth", 1e-69),
  ICOSIDILLIONTH("icosidillionth", 1e-66),
  ICOSIHENILLIONTH("icosihenillionth", 1e-63),
  ICOSILLIONTH("icosillionth", 1e-60),
  ENNEADEKILLIONTH("enneadekillionth", 1e-57),
  OKTADEKILLIONTH("oktadekillionth", 1e-54),
  HEPTADEKILLIONTH("heptadekillionth", 1e-51),
  HEXADEKILLIONTH("hexadekillionth", 1e-48),
  PENTADEKILLIONTH("pentadekillionth", 1e-45),
  TETRADEKILLIONTH("tetradekillionth", 1e-42),
  TRISDEKILLIONTH("trisdekillionth", 1e-39),
  DODEKILLIONTH("dodekillionth", 1e-36),
  HENDEKILLIONTH("hendekillionth", 1e-33),
  DEKILLIONTH("dekillionth", 1e-30),
  ENNILLIONTH("ennillionth", 1e-27),
  OKTILLIONTH("oktillionth", 1e-24),
  TETRILLIONTH("tetrillionth", 1e-12),
  HEPTILLIONTH("heptillionth", 1e-21),
  HEXILLIONTH("hexillionth", 1e-18),
  PENTILLIONTH("pentillionth", 1e-15),
  GILLIONTH("gillionth", 1e-9),
  MILLIONTH("millionth", 1e-6),
  THOUSANDTH("thousandth", 1e-3),
  NONE("", 1.0),
  THOUSAND("thousand", 1e3),
  MILLION("million", 1e6),
  GILLION("gillion", 1e9),
  TETRILLION("tetrillion", 1e12),
  PENTILLION("pentillion", 1e15),
  HEXILLION("hexillion", 1e18),
  HEPTILLION("heptillion", 1e21),
  OKTILLION("oktillion", 1e24),
  ENNILLION("ennillion", 1e27),
  DEKILLION("dekillion", 1e30),
  HENDEKILLION("hendekillion", 1e33),
  DODEKILLION("dodekillion", 1e36),
  TRISDEKILLION("trisdekillion", 1e39),
  TETRADEKILLION("tetradekillion", 1e42),
  PENTADEKILLION("pentadekillion", 1e45),
  HEXADEKILLION("hexadekillion", 1e48),
  HEPTADEKILLION("heptadekillion", 1e51),
  OKTADEKILLION("oktadekillion", 1e54),
  ENNEADEKILLION("enneadekillion", 1e57),
  ICOSILLION("icosillion", 1e60),
  ICOSIHENILLION("icosihenillion", 1e63),
  ICOSIDILLION("icosidillion", 1e66),
  ICOSITRILLION("icositrillion", 1e69),
  ICOSITETRILLION("icositetrillion", 1e72),
  ICOSIPENTILLION("icosipentillion", 1e75),
  ICOSIHEXILLION("icosihexillion", 1e78),
  ICOSIHEPTILLION("icosiheptillion", 1e81),
  ICOSIOKTILLION("icosioktillion", 1e84),
  ICOSIENNILLION("icosiennillion", 1e87),
  TRIACONTILLION("triacontillion", 1e90);

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

enum class BinaryPrefix(val longName: String, val symbol: String, val multiplier: BigInteger) {
  NONE("", "", BigInteger.ONE),
  KIBI("kibi", "Ki", BigInteger("1024e1")),
  MEBI("mebi", "Mi", BigInteger("1024e2")),
  GIBI("gibi", "Gi", BigInteger("1024e3")),
  TEBI("tebi", "Ti", BigInteger("1024e4")),
  PEBI("pebi", "Pi", BigInteger("1024e5")),
  EXBI("exbi", "Xi", BigInteger("1024e6")),
  ZEBI("zebi", "Zi", BigInteger("1024e7")),
  YOBI("yobi", "Yi", BigInteger("1024e8"));

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

enum class Prefix(val longName: String, val symbol: String, val multiplier: Double) {
  YOCTO("yocto", "y", 1e-24),
  ZEPTO("zepto", "z", 1e-21),
  ATTO("atto", "a", 1e-18),
  FEMTO("femto", "f", 1e-15),
  PICO("pico", "p", 1e-12),
  NANO("nano", "n", 1e-9),
  MICRO("micro", "µ", 1e-6),
  MILLI("milli", "m", 1e-3),
  CENTI("centi", "c", 1e-2),
  DECI("deci", "d", 1e-1),
  NONE("", "", 1e0),
  DECA("deca", "da", 1e1),
  HECTO("hecto", "h", 1e2),
  KILO("kilo", "k", 1e3),
  MEGA("mega", "M", 1e6),
  GIGA("giga", "G", 1e9),
  TERA("tera", "T", 1e12),
  PETA("peta", "P", 1e15),
  EXA("exa", "X", 1e18),
  ZETA("zeta", "Z", 1e21),
  YOTA("yota", "Y", 1e24);

  override fun toString(): String {
    return symbol
  }

  fun isLast(): Boolean {
    return ((this == YOTA) || (this == YOCTO))
  }

  fun up(): Prefix {
    return when {
      isLast() -> this
      this == HECTO -> KILO
      this == DECA -> KILO
      this == NONE -> KILO
      this == DECI -> NONE
      this == CENTI -> NONE
      else -> Prefix.values()[ordinal + 1]
    }
  }

  fun down(): Prefix {
    return when {
      isLast() -> this
      this == HECTO -> NONE
      this == DECA -> NONE
      this == NONE -> MILLI
      this == DECI -> MILLI
      this == CENTI -> MILLI
      else -> Prefix.values()[ordinal - 1]
    }
  }
}

infix fun <T: BaseUnit> Number.Y(measureUnit: MeasureUnit<T>): Measure<T> {
  return Measure(this.toDouble(),  Prefix.YOTA, measureUnit)
}
infix fun <T: BaseUnit> Number.Z(measureUnit: MeasureUnit<T>): Measure<T> {
  return Measure(this.toDouble(),  Prefix.ZETA, measureUnit)
}
infix fun <T: BaseUnit> Number.X(measureUnit: MeasureUnit<T>): Measure<T> {
  return Measure(this.toDouble(),  Prefix.EXA, measureUnit)
}
infix fun <T: BaseUnit> Number.P(measureUnit: MeasureUnit<T>): Measure<T> {
  return Measure(this.toDouble(),  Prefix.PETA, measureUnit)
}
infix fun <T: BaseUnit> Number.T(measureUnit: MeasureUnit<T>): Measure<T> {
  return Measure(this.toDouble(),  Prefix.TERA, measureUnit)
}
infix fun <T: BaseUnit> Number.G(measureUnit: MeasureUnit<T>): Measure<T> {
  return Measure(this.toDouble(),  Prefix.GIGA, measureUnit)
}
infix fun <T: BaseUnit> Number.M(measureUnit: MeasureUnit<T>): Measure<T> {
  return Measure(this.toDouble(),  Prefix.MEGA, measureUnit)
}
infix fun <T: BaseUnit> Number.k(measureUnit: MeasureUnit<T>): Measure<T> {
  return Measure(this.toDouble(),  Prefix.KILO, measureUnit)
}

infix fun <T: BaseUnit> Number.h(measureUnit: MeasureUnit<T>): Measure<T> {
  return Measure(this.toDouble(),  Prefix.HECTO, measureUnit)
}
infix fun <T: BaseUnit> Number.da(measureUnit: MeasureUnit<T>): Measure<T> {
  return Measure(this.toDouble(),  Prefix.DECA, measureUnit)
}

infix fun <T: BaseUnit> Number.ˍ(measureUnit: MeasureUnit<T>): Measure<T> {
  return Measure(this.toDouble(),  Prefix.NONE, measureUnit)
}
infix fun <T: BaseUnit> Number.d(measureUnit: MeasureUnit<T>): Measure<T> {
  return Measure(this.toDouble(),  Prefix.DECI, measureUnit)
}

infix fun <T: BaseUnit> Number.c(measureUnit: MeasureUnit<T>): Measure<T> {
  return Measure(this.toDouble(),  Prefix.CENTI, measureUnit)
}

infix fun <T: BaseUnit> Number.m(measureUnit: MeasureUnit<T>): Measure<T> {
  return Measure(this.toDouble(),  Prefix.MILLI, measureUnit)
}
infix fun <T: BaseUnit> Number.µ(measureUnit: MeasureUnit<T>): Measure<T> {
  return Measure(this.toDouble(),  Prefix.MICRO, measureUnit)
}
infix fun <T: BaseUnit> Number.n(measureUnit: MeasureUnit<T>): Measure<T> {
  return Measure(this.toDouble(),  Prefix.NANO, measureUnit)
}
infix fun <T: BaseUnit> Number.p(measureUnit: MeasureUnit<T>): Measure<T> {
  return Measure(this.toDouble(),  Prefix.PICO, measureUnit)
}
infix fun <T: BaseUnit> Number.f(measureUnit: MeasureUnit<T>): Measure<T> {
  return Measure(this.toDouble(),  Prefix.FEMTO, measureUnit)
}
infix fun <T: BaseUnit> Number.a(measureUnit: MeasureUnit<T>): Measure<T> {
  return Measure(this.toDouble(),  Prefix.ATTO, measureUnit)
}
infix fun <T: BaseUnit> Number.z(measureUnit: MeasureUnit<T>): Measure<T> {
  return Measure(this.toDouble(),  Prefix.ZEPTO, measureUnit)
}
infix fun <T: BaseUnit> Number.y(measureUnit: MeasureUnit<T>): Measure<T> {
  return Measure(this.toDouble(),  Prefix.YOCTO, measureUnit)
}

