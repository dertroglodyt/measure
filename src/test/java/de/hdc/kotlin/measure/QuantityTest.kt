package de.hdc.kotlin.measure

import org.junit.*
import org.junit.Assert.*

//todo
class QuantityTest {
  fun main() {
    // test toString()
    // all values == 0 returns an empty string
    assertEquals("", Quantity().toString())

    // short form for all values == 1 or -1
    assertEquals("mol·A·m·cd·g·K·s", Quantity(1, 1, 1, 1, 1, 1, 1).toString())
    assertEquals("1/mol·A·m·cd·g·K·s", Quantity(-1, -1, -1, -1, -1, -1, -1).toString())

    // long form for values > 1 or < -1
    assertEquals("mol^2·A^2·m^2·cd^2·g^2·K^2·s^2", Quantity(2, 2, 2, 2, 2, 2, 2).toString())
    assertEquals("1/mol^2·A^2·m^2·cd^2·g^2·K^2·s^2", Quantity(-2, -2, -2, -2, -2, -2, -2).toString())

    // mixed values
    assertEquals("mol^2·A^3·cd^4·K^2/m^2·g^4·s^3", Quantity(2, 3, -2, 4, -4, 2, -3).toString())
    assertEquals("mol^2·A·K^2/m·s^3", Quantity(2, 1, -1, 0, 0, 2, -3).toString())


    // Test equals()
    assertEquals(Quantity(0, 1, 2, 3, -1, -2, -3), Quantity(0, 1, 2, 3, -1, -2, -3))
    assertEquals(Quantity(), Quantity(0, 0, 0, 0, 0, 0, 0))
    assertFalse(Quantity(0, 1, 2, 3, -1, -2, -3).equals(Quantity()))
    assertFalse(Quantity(0, 1, 2, 3, -1, -2, -3).equals(Unit))


    // Test times()
    assertEquals("mol^2·A^2·m^2·cd^2·g^2·K^2·s^2"
        , Quantity(2, 2, 2, 2, 2, 2, 2).times(Quantity()).toString())
    assertEquals("mol^2·A^3·m^4·cd^5·g/s"
        , Quantity(2, 2, 2, 2, 2, 2, 2).times(Quantity(0, 1, 2, 3, -1, -2, -3)).toString())

    // Test div()
    assertEquals("mol^2·A^2·m^2·cd^2·g^2·K^2·s^2"
        , Quantity(2, 2, 2, 2, 2, 2, 2).div(Quantity()).toString())
    assertEquals("mol^2·A·g^3·K^4·s^5/cd"
        , Quantity(2, 2, 2, 2, 2, 2, 2).div(Quantity(0, 1, 2, 3, -1, -2, -3)).toString())

    println("Success!")
  }
}
