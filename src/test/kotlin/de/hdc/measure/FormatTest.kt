package de.hdc.measure

import de.hdc.measure.Prefix.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class FormatTest {

  @Test
  fun main() {
    // test pretty()
    // Default values: 4 padding, 3 digits
    assertEquals(" 230,000 g", Measure(230.0, g).pretty())
    // Default values: 4 padding, 3 digits
    assertEquals("-230,000 g", Measure(-230.0, g).pretty())
    // Default values: 4 padding, 3 digits, auto-stepup
    assertEquals("   2,300 kg", Measure(2300.0, g).pretty())
    // Default values: 4 padding, 3 digits, auto-stepdown
    assertEquals("   2,300 mg", Measure(0.0023, g).pretty())
    // Default values: 4 padding, 3 digits, auto-stepup overflow
    assertEquals("2300,000 Yg", Measure(2300000.0, ZETA, g).pretty())
    // Default values: 4 padding, 3 digits, auto-stepup overflow
    assertEquals("2300,000 Yg", Measure(2300.0, YOTA, g).pretty())
    // Default values: 4 padding, 3 digits, auto-stepup overflow, padding overflow
    assertEquals("23000,000 Yg", Measure(23000.0, YOTA, g).pretty())
    // Default values: 4 padding, 3 digits, auto-stepdown underflow
    assertEquals("   0,230 yg", Measure(0.23, YOCTO, g).pretty())
    // Default values: 4 padding, 3 digits, auto-stepdown underflow
    assertEquals("   0,000 yg", Measure(0.00023, YOCTO, g).pretty())

    // No optimization
    assertEquals("  23,000 g", Measure(23.0, g).pretty(3, 4, false))
    // No optimization, no digits
    assertEquals(" 2300 kg", (2300.0 k g).pretty(0, 5, false))
    assertEquals(" 2300 kg", Measure(2300.0, kg).pretty(0, 5, false))
    // optimization, no digits
    assertEquals("  230 t", Measure(230000.0, kg).pretty(0, 5))
  }

}
