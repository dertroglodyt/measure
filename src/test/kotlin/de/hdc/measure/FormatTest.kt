package de.hdc.measure

import de.hdc.measure.Prefix.*
import io.kotlintest.specs.FreeSpec
import org.junit.jupiter.api.Assertions.*

internal class FormatTest: FreeSpec() {

  init {
    "main" {
      // test format()
      // Default values: 4 padding, 3 digits
      assertEquals(" 230,000 g", Measure(230.0, g).format())
      // Default values: 4 padding, 3 digits
      assertEquals("-230,000 g", Measure(-230.0, g).format())
      // Default values: 4 padding, 3 digits, auto-stepup
      assertEquals("   2,300 kg", Measure(2300.0, g).format())
      // Default values: 4 padding, 3 digits, auto-stepdown
      assertEquals("   2,300 mg", Measure(0.0023, g).format())
      // Default values: 4 padding, 3 digits, auto-stepup overflow
      assertEquals("2 300,000 Yg", Measure(2300000.0, Z, g).format())
      // Default values: 4 padding, 3 digits, auto-stepup overflow
      assertEquals("2 300,000 Yg", Measure(2300.0, Y, g).format())
      // Default values: 4 padding, 3 digits, auto-stepup overflow, padding overflow
      assertEquals("23 000,000 Yg", Measure(23000.0, Y, g).format())
      // Default values: 4 padding, 3 digits, auto-stepdown underflow
      assertEquals("   0,230 yg", Measure(0.23, y, g).format())
      // Default values: 4 padding, 3 digits, auto-stepdown underflow
      assertEquals("   0,000 yg", Measure(0.00023, y, g).format())

      // No optimization
      assertEquals("  23,000 g", Measure(23.0, g).format(3, 4, false))
      // No optimization, no digits
      assertEquals("2 300 kg", (2300.0 k g).format(0, 5, false))
      assertEquals("2 300 kg", (2300.0 k g).format(0, 5, false))
      // optimization, no digits
      assertEquals("  230 t", (230000.0 k g).format(0, 5))
    }
  }
}
