package de.hdc.kotlin.measure

import de.hdc.kotlin.measure.Prefix.*
import org.junit.*
import org.junit.Assert.*

class MeasureTest {

  @Test
  fun main() {
    // test convertTo()
    assertEquals(Measure(1.0, MINUTE), Measure(60.0, SECOND).convertTo(MINUTE))
    assertEquals(Measure(1000.0, MINUTE), Measure(60.0, MeasureUnit(KILO, DU_SECOND)).convertTo(MINUTE))

    // test reciprocal()
    // must throw exception
    //assertEquals(Measure(0.0, MINUTE), Measure(0.0, MINUTE).reciprocal())
    assertEquals(Measure(1.0, MINUTE), Measure(1.0, MINUTE).reciprocal())
    assertEquals(Measure(0.5, MINUTE), Measure(2.0, MINUTE).reciprocal())
    assertEquals(Measure(-0.5, MINUTE), Measure(-2.0, MINUTE).reciprocal())


    // test inverse()
    // REALLY???
    assertEquals(Measure(-0.0, MINUTE), Measure(0.0, MINUTE).inverse())
    assertEquals(Measure(-1.0, MINUTE), Measure(1.0, MINUTE).inverse())
    assertEquals(Measure(1.0, MINUTE), Measure(-1.0, MINUTE).inverse())


    // test plus()
    // test minus()
    // test scalar(Double)
    // test scalar(Measure<SI_UNITLESS>)
    // test times()
    // test div()
    // test toString()
    assertEquals("1.23", Measure(1.23, UNITLESS).toString())
    assertEquals("-1.23", Measure(-1.23, UNITLESS).toString())
    assertEquals("1.23 kg", Measure(1.23, KILO_GRAM).toString())
    assertEquals("-1.23 kg", Measure(-1.23, KILO_GRAM).toString())
    assertEquals("1.23 m/s²", Measure(1.23, ACCELERATION).toString())


    // test compareTo()
    // must not compile
    //assertEquals(1, Measure(1.0, KILO_GRAM).compareTo(Measure(1.0, SECOND)))
    assertEquals(1, Measure(1.0, KILO_GRAM).compareTo(Measure(1.0, GRAM)))
    assertEquals(-1, Measure(1.0, PETA, GRAM).compareTo(Measure(1.0, YOTA, GRAM)))
    assertEquals(0, Measure(1.0, KILO_GRAM).compareTo(Measure(1.0, KILO_GRAM)))
    assertEquals(-1, Measure(1.0, KILO_GRAM).compareTo(Measure(2.0, KILO_GRAM)))


    // test toDouble()
    assertEquals(1000.0, Measure(1.0, KILO_GRAM).toDouble(), 0.0)
    assertEquals(1000000.0, Measure(1.0, TON).toDouble(), 0.0)
    assertEquals(1.0, Measure(1.0, GRAM).toDouble(), 0.0)
    assertEquals(0.0, Measure(0.0, GRAM).toDouble(), 0.0)
    assertEquals(-1.0, Measure(-1.0, GRAM).toDouble(), 0.0)
    assertEquals(1.0e9, Measure(1.0, GIGA, GRAM).toDouble(), 0.0)

  }
}
