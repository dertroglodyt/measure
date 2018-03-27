package de.hdc.kotlin.measure

import de.hdc.kotlin.measure.Prefix.*
import org.junit.*
import org.junit.Assert.*

class FormatTest {
  @Test
  fun main() {
    // test pretty()
    // Default values: 4 padding, 3 digits
    assertEquals(" 230,000 g", pretty(Measure(230.0, GRAM)))
    // Default values: 4 padding, 3 digits
    assertEquals("-230,000 g", pretty(Measure(-230.0, GRAM)))
    // Default values: 4 padding, 3 digits, auto-stepup
    assertEquals("   2,300 kg", pretty(Measure(2300.0, GRAM)))
    // Default values: 4 padding, 3 digits, auto-stepdown
    assertEquals("   2,300 mg", pretty(Measure(0.0023, GRAM)))
    // Default values: 4 padding, 3 digits, auto-stepup overflow
    assertEquals("2300,000 Yg", pretty(Measure(2300000.0, MeasureUnit(ZETA, GRAM))))
    // Default values: 4 padding, 3 digits, auto-stepup overflow
    assertEquals("2300,000 Yg", pretty(Measure(2300.0, MeasureUnit(YOTA, GRAM))))
    // Default values: 4 padding, 3 digits, auto-stepup overflow, padding overflow
    assertEquals("23000,000 Yg", pretty(Measure(23000.0, MeasureUnit(YOTA, GRAM))))
    // Default values: 4 padding, 3 digits, auto-stepdown underflow
    assertEquals("   0,230 yg", pretty(Measure(0.23, MeasureUnit(YOCTO, GRAM))))
    // Default values: 4 padding, 3 digits, auto-stepdown underflow
    assertEquals("   0,000 yg", pretty(Measure(0.00023, MeasureUnit(YOCTO, GRAM))))

    // No optimization
    assertEquals("  23,000 kg", pretty(Measure(23.0, KILO_GRAM), 3, 4, false))
    // No optimization, no digits
    assertEquals(" 2300 kg", pretty(Measure(2300.0, KILO_GRAM), 0, 5, false))
    // optimization, no digits
    assertEquals("  230 t", pretty(Measure(230000.0, KILO_GRAM), 0, 5))
  }

}
