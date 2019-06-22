package de.hdc.measure

import ch.obermuhlner.math.big.BigFloat.*
import de.hdc.measure.Prefix.*
import io.kotlintest.*
import io.kotlintest.matchers.doubles.*
import io.kotlintest.specs.*
import org.junit.jupiter.api.Assertions.*
import kotlin.Float
import kotlin.Short

internal class MeasureTest : FreeSpec() {

//  val reader = autoClose(StringReader("xyz"))

  init {
    "numbers of different precision" {
      3.04 k g shouldBe Measure(context(15).valueOf("3.040"), MeasureUnit(k, g))
    }

    "deprecate float and double" {
      shouldThrowExactly<IllegalAccessException> {
        val f: Float = 1.0f
        println("Should not print! $f")
      }

      shouldThrowExactly<IllegalAccessException> {
        val d: Short = 2.toShort()
        println("Should not print! $d")
      }
    }

    "main" {
      assertEquals(
        (1273.15 ˍ K).convertTo(`°C`).toDouble(),
        (1.0 k `°C`).toDouble(), 0.0001
      )

      assertEquals(1.2 ˍ min, 1.2 ˍ min)

      // test convertTo()
      assertEquals(1.0 ˍ min, (60.0 ˍ s).convertTo(min))
      assertEquals(60.0 ˍ s, (1.0 ˍ min).convertTo(s))

      assertEquals(60.0 ˍ min, (1.0 ˍ h).convertTo(min))
      assertEquals(1.0 ˍ h, (60.0 ˍ min).convertTo(h))

      assertEquals(
        Measure(1000.0, min)
        , Measure(60.0, k, s).convertTo(min)
      )

      assertEquals(Measure(273.15, K), Measure(0.0, `°C`).convertTo(K))
      assertEquals(Measure(273.15, K).convertTo(`°C`), Measure(0.0, `°C`))

      (1000 ˍ `°C`) shouldBe (1.0 k `°C`).convertTo(`°C`)
      (0.001 M `°C`) shouldBe (1.0 k `°C`).convertTo(`°C`)
      (1000 ˍ `°C`) shouldBe (1.0 k `°C`)
      (0.001 M `°C`) shouldBe (1.0 k `°C`)

      (1000.0000000000001 ˍ `°C`) shouldNotBe (1.0 k `°C`)
      (1000.0000000000001 ˍ `°C`).aproximates(1.0 k `°C`, 1e-10 ˍ UNITLESS) shouldBe true

      1273.15 ˍ K shouldBe (1.0 k `°C`).convertTo(K)
      (1273.15 ˍ K).convertTo(`°C`).toDouble() shouldBe ((1.0 k `°C`).toDouble() plusOrMinus 0.0001)

      shouldThrow<IllegalArgumentException> {
        ((1.0 k g) * (1.0 ˍ m)).convertTo(g)
      }

      (1.0 ˍ N) shouldBe ((((1.0 k g) * (1.0 ˍ m)) / (1.0 ˍ s)) / (1.0 ˍ s)).convertTo(N)

      // test reciprocal()
      // must throw exception
      shouldThrow<ArithmeticException> { (0.0 ˍ min).reciprocal() }
      (1.0 ˍ m.reciprocal()) shouldBe (1.0 ˍ m).reciprocal()
      (0.5 ˍ m.reciprocal()) shouldBe (2.0 ˍ m).reciprocal()
      (-0.5 ˍ m.reciprocal()) shouldBe (-2.0 ˍ m).reciprocal()


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
      assertEquals("1.23 kg", (1.23 k g).toString())
      assertEquals("-1.23 kg", (-1.23 k g).toString())
      assertEquals("1.23 m/s²", Measure(1.23, m_s2).toString())


      // test compareTo()
      // must not compile
      //assertEquals(1, Measure(1.0, kg).compareTo(Measure(1.0, SECOND)))
//      assertEquals(1, (1.0 k g).compareTo(Measure(1.0, g)))
      assertEquals(-1, Measure(1.0, P, g).compareTo(Measure(1.0, Y, g)))
      assertEquals(0, (1.0 k g).compareTo((1.0 k g)))
      assertEquals(-1, (1.0 k g).compareTo((2.0 k g)))


      // test toDouble()
      assertEquals(1.0, (1.0 k g).toDouble())
      assertEquals(1000.0, Measure(1.0, t).toDouble())
      assertEquals(0.001, Measure(1.0, g).toDouble())
      assertEquals(0.0, Measure(0.0, g).toDouble())
      assertEquals(-0.001, Measure(-1.0, g).toDouble())
      assertEquals(1.0e6, Measure(1.0, G, g).toDouble())

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
      (((10.0 k g) / (5.0 ˍ g)) * (3 ˍ s)) shouldBe (6000.0 ˍ s)
      ((10.0 k g) / (5.0 ˍ g) * (3 ˍ s)).unit shouldBe s
      ((10.0 k g) / (5.0 ˍ g) * (3 ˍ s)).convertTo(min) shouldBe (100.0 ˍ min)
      ((10.0 k m) * (10.0 k m)) shouldBe (1.0e8 ˍ m2)
      ((10.0 k g) * (10.0 k g)).value.toDouble() shouldBe (100.0)
      ((10.0 k g) * (10.0 ˍ g)).value.toDouble() shouldBe (0.1)
    }
  }
}

