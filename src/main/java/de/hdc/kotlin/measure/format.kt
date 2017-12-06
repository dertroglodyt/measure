package de.hdc.kotlin.measure

fun pretty(m: Measure<*>, decimals: Int = 3, padding: Int = 4, optimize: Boolean = true): String {
  if (decimals < 0) {
    throw IllegalArgumentException("Negative decimal places not allowed!")
  }
  if (padding < 0) {
    throw IllegalArgumentException("Negative padding not allowed!")
  }
  var v = m.value
  var p = m.unit.prefix
  if (optimize) {
      val result = findOptimalPrefix(m.value, m.unit.prefix)
      v = result.component1()
      p = result.component2()
  }
  var s = v.format(decimals).replace('.', ',')
  val comma = if (decimals > 0) 1 else 0
  while (s.length < decimals + padding + comma) {
    s = " " + s
  }
  val unit = p.toString() + m.unit.unit.symbol
  return s + " " + unit.replace("Mg", "t")
}

private fun findOptimalPrefix(value: Double, prefix: Prefix): Pair<Double, Prefix> {
  var v = value
  var p = prefix
  if (Math.abs(v) < 1.0) {
    while ((Math.abs(v) < 1.0) && (!p.isLast())) {
      p = p.up()
      v *= 1000.0
    }
  } else if (Math.abs(v) >= 1000.0){
    while ((Math.abs(v) >= 1000.0) && (!p.isLast())) {
      p = p.down()
      v /= 1000.0
    }
  }
  return Pair(v, p)
}

private fun Double.format(digits: Int) = java.lang.String.format("%.${digits}f", this)!!

