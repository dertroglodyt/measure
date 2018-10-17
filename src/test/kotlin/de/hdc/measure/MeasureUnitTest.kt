package de.hdc.measure

import de.hdc.measure.Prefix.*
import io.kotlintest.*
import io.kotlintest.matchers.boolean.shouldBeFalse
import io.kotlintest.matchers.boolean.shouldBeTrue
import io.kotlintest.matchers.plusOrMinus
import io.kotlintest.properties.Gen
import io.kotlintest.specs.FreeSpec

//@RunWith(KTestJUnitRunner::class)
class MeasureUnitTest: FreeSpec() {

  init {
    "Random values".config(timeout = 10.seconds) {
      forAll(Gen.double().random().take(10).toList()) { v: Double ->
        Measure(v, t).toDouble() shouldBe((1000.0 * v) plusOrMinus 1e-16)
      }
    }

    "Conversions" {
      360 ˍ `°` shouldBe ((2.0*Math.PI) ˍ rad)
      (10.0 k `°C`).convertTo(K) shouldBe (10273.15 ˍ K)
      (1.0 ˍ `°C`) shouldNotBe (1.0 ˍ K)
      ((60*60*24) ˍ s) shouldBe (1 ˍ d)
      (1 ˍ d).convertTo(s) shouldBe (86400 ˍ s)
      (1 ˍ d).convertTo(s) shouldNotBe (86401 ˍ s)
    }

    "Test equals()" - {
      "predefined units" {
        `°C`.shouldBe(`°C`)
        K.shouldNotBe(`°C`)

        g.shouldNotBe(Unit)
        g.shouldBe(g)
        g.shouldNotBe(MeasureUnit(k, g))
      }

      "predefined & SI units" {
      }

      "predefined & COMBINED" {
        MeasureUnit(NONE, "", "", SI_COMBINED(Quantity(0, 0, 0, 0, 1, 0, 0)))
                .shouldNotBe(MeasureUnit(k, g))
      }

      "COMBINED" {
        MeasureUnit(k, "", "", SI_COMBINED(Quantity(0, -2, 0, 0, 1, 0, 0)))
                .shouldBe(MeasureUnit(k, "", "", SI_COMBINED(Quantity(0, -2, 0, 0, 1, 0, 0))))
      }
    }

    "Test isEquivalentTo()" {
      g.isEquivalentTo(m).shouldBeFalse()

      g.isEquivalentTo(MeasureUnit(k, g)).shouldBeTrue()
      m.isEquivalentTo(AU).shouldBeTrue()

      MeasureUnit(k, "", "", SI_COMBINED(Quantity(0, -2, 0, 0, 1, 0, 0)))
              .isEquivalentTo(MeasureUnit(k, "", "", SI_COMBINED(Quantity(0, -2, 0, 0, 1, 0, 0))))
              .shouldBeTrue()

      //todo
//    "Test valueOf()" - {
//      "not recognized" {
//        Unit shouldBe measureUnitFrom("zzz").getOrElse(Unit)
//      }
//      "some common units" {
//        UNITLESS shouldBe measureUnitFrom("").getOrElse(Unit)
//        kg shouldBe measureUnitFrom("kg").getOrElse(Unit)
//        kg shouldBe measureUnitFrom("kg").getOrElse(Unit)
//        AU shouldBe measureUnitFrom("AU").getOrElse(Unit)
//        `°C` shouldBe measureUnitFrom("°C").getOrElse(Unit)
//      }
//    }
    }
  }
}
