package de.hdc.measure

import de.hdc.measure.Prefix.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.lang.IllegalArgumentException

internal class MeasureTest {

//  val reader = autoClose(StringReader("xyz"))

  @Test
  fun main() {
    assertEquals((1273.15 ˍ K).convertTo(`°C`).toDouble(),
           Measure(1.0, KILO, `°C`).toDouble(), 0.0001)

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

    assertEquals(Measure(273.15, K), Measure(0.0, `°C`).convertTo(K))
    assertEquals(Measure(273.15, K).convertTo(`°C`), Measure(0.0, `°C`))
    assertEquals(1000 ˍ `°C`, 1.0 k `°C`)
    assertEquals(1273.15 ˍ K, (1.0 k `°C`).convertTo(K))
//    (1273.15 ˍ K).convertTo(`°C`).toDouble() shouldBe((1.0 k `°C`).toDouble() plusOrMinus(0.0001))
//
//    shouldThrow<IllegalArgumentException> {
//      assertEquals(Measure(1000.0, g) * Measure(1.0, m)
//            , ((Measure(1.0, kg) * Measure(1.0, m)).convertTo(g)))
//    }

//    println((149.0 G m).convertTo(AU))
//    println((1 ˍ AU).convertTo(m).format())
//    println(((((1.0 k g) * (1.0 ˍ m)) / (1.0 ˍ  s)) / (1.0 ˍ  s)).toDouble())
//    println((((1.0 k g) * (1.0 ˍ m)) / (1.0 ˍ  s)) / (1.0 ˍ  s))
//    println(((((1.0 k g) * (1.0 ˍ m)) / (1.0 ˍ  s)) / (1.0 ˍ  s)).convertTo(N))
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
    assertEquals("1.23 kg", Measure(1.23, kg).toString())
    assertEquals("-1.23 kg", Measure(-1.23, kg).toString())
    assertEquals("1.23 m/s²", Measure(1.23, m_s2).toString())


    // test compareTo()
    // must not compile
    //assertEquals(1, Measure(1.0, kg).compareTo(Measure(1.0, SECOND)))
    assertEquals(1, Measure(1.0, kg).compareTo(Measure(1.0, g)))
    assertEquals(-1, Measure(1.0, PETA, g).compareTo(Measure(1.0, YOTA, g)))
    assertEquals(0, Measure(1.0, kg).compareTo(Measure(1.0, kg)))
    assertEquals(-1, Measure(1.0, kg).compareTo(Measure(2.0, kg)))


    // test toDouble()
    assertEquals(1000.0, Measure(1.0, kg).toDouble())
    assertEquals(1000000.0, Measure(1.0, t).toDouble())
    assertEquals(1.0, Measure(1.0, g).toDouble())
    assertEquals(0.0, Measure(0.0, g).toDouble())
    assertEquals(-1.0, Measure(-1.0, g).toDouble())
    assertEquals(1.0e9, Measure(1.0, GIGA, g).toDouble())

  }

  @Test
  fun format() {
    assertEquals(" NaN", Measure.NaN.format())

    assertEquals("3,123 kg", (3.12345 k g).format(padding = 0))
    assertEquals("   3,123 kg", (3.12345 k g).format())

    assertEquals("  -0 kg", (-0.0 k g).format(0))
    assertEquals("  -0 kg", (-0.0 k g).format(0, optimize = false))
    assertEquals("   3 kg", (3.12345 k g).format(0))
    assertEquals("   3,1 kg", (3.12345 k g).format(1))
    assertEquals("   3,123 5 kg", (3.12345 k g).format(4))

    assertEquals("  -3,123 kg", (-3.12345 k g).format())
    assertEquals("  -3 kg", (-3.12345 k g).format(0))
    assertEquals("  -3,1 kg", (-3.12345 k g).format(1))
    assertEquals("  -3,123 5 kg", (-3.12345 k g).format(4))

    assertEquals("   0,123 kg", (0.12345 k g).format(optimize = false))
    assertEquals("   1,123 kg", (1.12345 k g).format(optimize = false))
    assertEquals("  12,123 kg", (12.12345 k g).format(optimize = false))
    assertEquals(" 123,123 kg", (123.12345 k g).format(optimize = false))
    assertEquals("1 234,123 kg", (1234.12345 k g).format(optimize = false))
    assertEquals("12 345,123 kg", (12345.12345 k g).format(optimize = false))
    assertEquals("123 456,123 kg", (123456.12345 k g).format(optimize = false))
    assertEquals("1 234 567,123 kg", (1234567.12345 k g).format(optimize = false))
    assertEquals("12 345 678,123 kg", (12345678.12345 k g).format(optimize = false))
    assertEquals("123 456 789,123 kg", (123456789.12345 k g).format(optimize = false))

    assertEquals("  -0,123 kg", (-0.12345 k g).format(optimize = false))
    assertEquals("  -1,123 kg", (-1.12345 k g).format(optimize = false))
    assertEquals(" -12,123 kg", (-12.12345 k g).format(optimize = false))
    assertEquals("-123,123 kg", (-123.12345 k g).format(optimize = false))
    assertEquals("-1 234,123 kg", (-1234.12345 k g).format(optimize = false))
    assertEquals("-12 345,123 kg", (-12345.12345 k g).format(optimize = false))
    assertEquals("-123 456,123 kg", (-123456.12345 k g).format(optimize = false))
    assertEquals("-1 234 567,123 kg", (-1234567.12345 k g).format(optimize = false))
    assertEquals("-12 345 678,123 kg", (-12345678.12345 k g).format(optimize = false))
    assertEquals("-123 456 789,123 kg", (-123456789.12345 k g).format(optimize = false))

    assertEquals("   0,1 kg", (0.1 k g).format(1, optimize = false))
    assertEquals("   0,12 kg", (0.12 k g).format(2, optimize = false))
    assertEquals("   0,123 kg", (0.123 k g).format(3, optimize = false))
    assertEquals("   0,123 4 kg", (0.1234 k g).format(4, optimize = false))
    assertEquals("   0,123 45 kg", (0.12345 k g).format(5, optimize = false))
    assertEquals("   0,123 456 kg", (0.123456 k g).format(6, optimize = false))
    assertEquals("   0,123 456 7 kg", (0.1234567 k g).format(7, optimize = false))
    assertEquals("   0,123 456 78 kg", (0.12345678 k g).format(8, optimize = false))
    assertEquals("   0,123 456 789 kg", (0.123456789 k g).format(9, optimize = false))

    assertThrows(IllegalArgumentException().javaClass, {(3.12345 k g).format(-1)})
  }
}

