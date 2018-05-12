package de.hdc.measure

import de.hdc.measure.Prefix.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class MeasureTest {

//  val reader = autoClose(StringReader("xyz"))

  @Test
  fun main() {
    assertEquals((1273.15 ˍ K).convertTo(`℃`).toDouble(),
           Measure(1.0, KILO, `℃`).toDouble(), 0.0001)

    assertEquals(Measure(1.2, min), Measure(1.2, NONE, min))

    // test convertTo()
    assertEquals(1.0 ˍ min, (60.0 ˍ s).convertTo(min))
    assertEquals(Measure(60.0, s)
            , Measure(1.0, min).convertTo(s))

    assertEquals(Measure(60.0, min)
            , Measure(1.0, h).convertTo(min))
    assertEquals(Measure(1.0, h)
            , Measure(60.0, min).convertTo(h))

    assertEquals(Measure(1000.0, min)
            , Measure(60.0, KILO, s).convertTo(min))

    assertEquals(Measure(273.15, K), Measure(0.0, `℃`).convertTo(K))
    assertEquals(Measure(273.15, K).convertTo(`℃`), Measure(0.0, `℃`))
    assertEquals(1000 ˍ `℃`, 1.0 k `℃`)
    assertEquals(1273.15 ˍ K, (1.0 k `℃`).convertTo(K))
//    (1273.15 ˍ K).convertTo(`℃`).toDouble() shouldBe((1.0 k `℃`).toDouble() plusOrMinus(0.0001))
//
//    shouldThrow<IllegalArgumentException> {
//      assertEquals(Measure(1000.0, g) * Measure(1.0, m)
//            , ((Measure(1.0, kg) * Measure(1.0, m)).convertTo(g)))
//    }

//    println((149.0 G m).convertTo(AU))
//    println((1 ˍ AU).convertTo(m).pretty())
    println(((((1.0 k g) * (1.0 ˍ m)) / (1.0 ˍ  s)) / (1.0 ˍ  s)))
    println(((((1.0 k g) * (1.0 ˍ m)) / (1.0 ˍ  s)) / (1.0 ˍ  s)).convertTo(N))
    assertEquals(Measure(1.0, N)
            , ((((1.0 k g) * (1.0 ˍ m)) / (1.0 ˍ  s)) / (1.0 ˍ  s)).convertTo(N))


    // test reciprocal()
    // must throw exception
    //assertEquals(Measure(0.0, MINUTE), Measure(0.0, MINUTE).reciprocal())
    assertEquals(Measure(1.0, m), Measure(1.0, m).reciprocal())
    assertEquals(Measure(0.5, m), Measure(2.0, m).reciprocal())
    assertEquals(Measure(-0.5, m), Measure(-2.0, m).reciprocal())


    // test inverse()
    // REALLY???
    assertEquals(Measure(-0.0, m), Measure(0.0, m).inverse())
    assertEquals(Measure(-1.0, m), Measure(1.0, m).inverse())
    assertEquals(Measure(1.0, m), Measure(-1.0, m).inverse())


    // test plus()
    // test minus()
    // test scalar(Double)
    // test scalar(Measure<SI_UNITLESS>)
    // test times()
    // test div()



    // test toString()
    assertEquals("1.23", Measure(1.23, UNITLESS).toString())
    assertEquals("-1.23", Measure(-1.23, UNITLESS).toString())
    assertEquals("1.23 g", Measure(1.23, kg).toString())
    assertEquals("-1.23 g", Measure(-1.23, kg).toString())
    assertEquals("1.23 m/s²", Measure(1.23, m_s2).toString())


    // test compareTo()
    // must not compile
    //assertEquals(1, Measure(1.0, kg).compareTo(Measure(1.0, SECOND)))
    assertEquals(1, Measure(1.0, kg).compareTo(Measure(1.0, g)))
    assertEquals(-1, Measure(1.0, PETA, g).compareTo(Measure(1.0, YOTA, g)))
    assertEquals(0, Measure(1.0, kg).compareTo(Measure(1.0, kg)))
    assertEquals(-1, Measure(1.0, kg).compareTo(Measure(2.0, kg)))


    // test toDouble()
    assertEquals(1000.0, Measure(1.0, kg).toDouble(), 0.0)
    assertEquals(1000000.0, Measure(1.0, t).toDouble(), 0.0)
    assertEquals(1.0, Measure(1.0, g).toDouble(), 0.0)
    assertEquals(0.0, Measure(0.0, g).toDouble(), 0.0)
    assertEquals(-1.0, Measure(-1.0, g).toDouble(), 0.0)
    assertEquals(1.0e9, Measure(1.0, GIGA, g).toDouble(), 0.0)

  }
}
