package de.hdc.measure

import io.kotlintest.*
import io.kotlintest.matchers.boolean.shouldBeFalse
import io.kotlintest.matchers.boolean.shouldBeTrue
import io.kotlintest.matchers.plusOrMinus
import io.kotlintest.properties.Gen
import io.kotlintest.specs.FreeSpec
import org.junit.jupiter.api.Assertions.*

class QuantityTest: FreeSpec() {

  init {
    "main" {
      // test toString()
      // all values == 0 returns an empty string
      assertEquals("", Quantity().toString())

      // short form for all values == 1 or -1
      assertEquals("mol·A·m·cd·kg·K·s", Quantity(1, 1, 1, 1, 1, 1, 1).toString())
      assertEquals("1/mol·A·m·cd·kg·K·s", Quantity(-1, -1, -1, -1, -1, -1, -1).toString())

      // long form for values > 1 or < -1
      assertEquals("mol^2·A^2·m^2·cd^2·kg^2·K^2·s^2", Quantity(2, 2, 2, 2, 2, 2, 2).toString())
      assertEquals("1/mol^2·A^2·m^2·cd^2·kg^2·K^2·s^2", Quantity(-2, -2, -2, -2, -2, -2, -2).toString())

      // mixed values
      assertEquals("mol^2·A^3·cd^4·K^2/m^2·kg^4·s^3", Quantity(2, 3, -2, 4, -4, 2, -3).toString())
      assertEquals("mol^2·A·K^2/m·s^3", Quantity(2, 1, -1, 0, 0, 2, -3).toString())


      // Test equals()
      assertEquals(Quantity(0, 1, 2, 3, -1, -2, -3), Quantity(0, 1, 2, 3, -1, -2, -3))
      assertEquals(Quantity(), Quantity(0, 0, 0, 0, 0, 0, 0))
      assertFalse(Quantity(0, 1, 2, 3, -1, -2, -3) == Quantity())
      assertFalse(Quantity(0, 1, 2, 3, -1, -2, -3).equals(Quantity()))
      assertFalse(Quantity(0, 1, 2, 3, -1, -2, -3).equals(Unit))

      // Test times()
      assertTrue("mol^2·A^2·m^2·cd^2·kg^2·K^2·s^2".equals(
              (Quantity(2, 2, 2, 2, 2, 2, 2) * Quantity()).toString()))
      assertEquals("mol^2·A^3·m^4·cd^5·kg/s"
              , (Quantity(2, 2, 2, 2, 2, 2, 2)* Quantity(0, 1, 2, 3, -1, -2, -3)).toString())

      // Test div()
      assertEquals("mol^2·A^2·m^2·cd^2·kg^2·K^2·s^2"
              , (Quantity(2, 2, 2, 2, 2, 2, 2) / Quantity()).toString())
      assertEquals("mol^2·A·kg^3·K^4·s^5/cd"
              , (Quantity(2, 2, 2, 2, 2, 2, 2)/ Quantity(0, 1, 2, 3, -1, -2, -3)).toString())

      println("Success!")
    }
  }
}
