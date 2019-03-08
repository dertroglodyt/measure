package de.hdc.measure

import de.hdc.measure.Prefix.*
import io.kotlintest.*
import io.kotlintest.matchers.doubles.*
import io.kotlintest.specs.FreeSpec
import org.junit.jupiter.api.Assertions.*
import java.lang.IllegalArgumentException

internal class BigMeasureTest: FreeSpec() {

//  val reader = autoClose(StringReader("xyz"))

  init {
    "main" {
      assertEquals((1273.15 ˍ K).convertTo(`°C`).toDouble(),
              BigMeasure(1.0, k, `°C`).toDouble(), 0.0001)

      assertEquals(BigMeasure(1.2, min), BigMeasure(1.2, NONE, min))

      // test convertTo()
      assertEquals(1.0 ˍ min, (60.0 ˍ s).convertTo(min))
      assertEquals(BigMeasure(60.0, s)
              , BigMeasure(1.0, min).convertTo(s))

      assertEquals(BigMeasure(60.0, min)
              , BigMeasure(1.0, h).convertTo(min))
      assertEquals(BigMeasure(1.0, h)
              , BigMeasure(60.0, min).convertTo(h))

      assertEquals(BigMeasure(1000.0, min)
              , BigMeasure(60.0, k, s).convertTo(min))

      assertEquals(BigMeasure(273.15, K), BigMeasure(0.0, `°C`).convertTo(K))
      assertEquals(BigMeasure(273.15, K).convertTo(`°C`), BigMeasure(0.0, `°C`))

      // Conversion results in 1000.0000000000001 °C
      (1000 ˍ `°C`) shouldNotBe (1.0 k `°C`).convertTo(`°C`)
      should { (1000 ˍ `°C`) aproximates  (1.0 k `°C`).convertTo(`°C`) }

      (1000.0000000000001 ˍ `°C`) shouldNotBe (1.0 k `°C`)
      should { (1000.0000000000001 ˍ `°C`) aproximates (1.0 k `°C`) }

      1273.15 ˍ K shouldBe (1.0 k `°C`).convertTo(K)
      (1273.15 ˍ K).convertTo(`°C`).toDouble() shouldBe((1.0 k `°C`).toDouble() plusOrMinus 0.0001)

      shouldThrow<IllegalArgumentException> {
        ((1.0 k g) * (1.0 ˍ m)).convertTo(g)
      }

      (1.0 ˍ N) shouldBe ((((1.0 k g) * (1.0 ˍ m)) / (1.0 ˍ s)) / (1.0 ˍ s)).convertTo(N)

      // test reciprocal()
      // must throw exception
      shouldThrow<NumberFormatException> { (0.0 ˍ min).reciprocal() }
      (1.0 ˍ m) shouldBe (1.0 ˍ m).reciprocal()
      (0.5 ˍ m) shouldBe (2.0 ˍ m).reciprocal()
      (-0.5 ˍ m) shouldBe (-2.0 ˍ m).reciprocal()


      // test inverse()
      // REALLY???
      assertEquals(BigMeasure(-0.0, m), BigMeasure(0.0, m).inverse())
      assertEquals(BigMeasure(-1.0, m), BigMeasure(1.0, m).inverse())
      assertEquals(BigMeasure(1.0, m), BigMeasure(-1.0, m).inverse())


      // test plus()
      // test minus()
      // test scalar(Double)
      // test scalar(BigMeasure<SI_UNITLESS>)
      // test times()
      // test div()


      // test toString()
      assertEquals("1.23", BigMeasure(1.23, UNITLESS).toString())
      assertEquals("-1.23", BigMeasure(-1.23, UNITLESS).toString())
      assertEquals("1.23 kg", (1.23 k g).toString())
      assertEquals("-1.23 kg", (-1.23 k g).toString())
      assertEquals("1.23 m/s²", BigMeasure(1.23, m_s2).toString())


      // test compareTo()
      // must not compile
      //assertEquals(1, BigMeasure(1.0, kg).compareTo(BigMeasure(1.0, SECOND)))
//      assertEquals(1, (1.0 k g).compareTo(BigMeasure(1.0, g)))
      assertEquals(-1, BigMeasure(1.0, P, g).compareTo(BigMeasure(1.0, Y, g)))
      assertEquals(0, (1.0 k g).compareTo((1.0 k g)))
      assertEquals(-1, (1.0 k g).compareTo((2.0 k g)))


      // test toDouble()
      assertEquals(1.0, (1.0 k g).toDouble())
      assertEquals(1000.0, BigMeasure(1.0, t).toDouble())
      assertEquals(0.001, BigMeasure(1.0, g).toDouble())
      assertEquals(0.0, BigMeasure(0.0, g).toDouble())
      assertEquals(-0.001, BigMeasure(-1.0, g).toDouble())
      assertEquals(1.0e6, BigMeasure(1.0, G, g).toDouble())

    }

    "format" {
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

      assertThrows(IllegalArgumentException().javaClass) { (3.12345 k g).format(-1) }
    }

    "calculations" {
      ((10.0 k g) / (5.0 ˍ g) * (3 ˍ s)) shouldBe(6000.0 ˍ s)
      ((10.0 k g) / (5.0 ˍ g) * (3 ˍ s)).unit shouldBe s
      ((10.0 k g) / (5.0 ˍ g) * (3 ˍ s)).convertTo(min) shouldBe(100.0 ˍ min)
      ((10.0 k m) * (10.0 k m)) shouldBe(1.0e8 ˍ m2)
      ((10.0 k g) * (10.0 k g)).value shouldBe(100.0)
      ((10.0 k g) * (10.0 ˍ g)).value shouldBe(0.1)
    }
  }
}

