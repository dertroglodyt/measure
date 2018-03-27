package de.hdc.kotlin.measure

import com.danneu.result.*
import de.hdc.kotlin.measure.Prefix.*
import org.junit.*
import org.junit.Assert.*


class MeasureUnitTest {

  @Test
  fun main() {
    // test equals()
    assertNotEquals(GRAM, Unit)
    assertEquals(GRAM, GRAM)
    assertNotEquals(GRAM, KILO_GRAM)
    assertEquals(KILO_GRAM, MeasureUnit(KILO, GRAM))

    assertEquals(GRAM, COMBINED(UNKNOWN(Quantity(mass = 1))))

    assertEquals(KILO_GRAM, MeasureUnit(KILO, COMBINED(UNKNOWN(Quantity(mass = 1)))))
    assertNotEquals(KILO_GRAM, MeasureUnit(NONE, COMBINED(UNKNOWN(Quantity(mass = 1)))))

    assertEquals(MeasureUnit(KILO, COMBINED(UNKNOWN(Quantity(mass = 1)))), KILO_GRAM)
    assertNotEquals(MeasureUnit(NONE, COMBINED(UNKNOWN(Quantity(mass = 1)))), KILO_GRAM)

    assertEquals(MeasureUnit(KILO, COMBINED(UNKNOWN(Quantity(electricCurrent = -2, mass = 1))))
        , MeasureUnit(KILO, COMBINED(UNKNOWN(Quantity(electricCurrent = -2, mass = 1)))))

    // Test isEquivalentTo()
    assertFalse(GRAM.isEquivalentTo(METER))

    assertTrue(GRAM.isEquivalentTo(KILO_GRAM))
    assertTrue(METER.isEquivalentTo(AU))

    assertTrue(MeasureUnit(KILO, COMBINED(UNKNOWN(Quantity(electricCurrent = -2, mass = 1))))
        .isEquivalentTo(MeasureUnit(KILO, COMBINED(UNKNOWN(Quantity(electricCurrent = -2, mass = 1))))))


    // Test valueOf()
    assertEquals(Unit, measureUnitFrom("zzz").getOrElse(Unit))
    assertEquals(UNITLESS, measureUnitFrom("").getOrElse(Unit))
    assertEquals(GRAM, measureUnitFrom("g").getOrElse(Unit))
    assertEquals(KILO_GRAM, measureUnitFrom("kg").getOrElse(Unit))
    assertEquals(AU, measureUnitFrom("AU").getOrElse(Unit))
    assertEquals(GRADE_CELSIUS, measureUnitFrom("°C").getOrElse(Unit))
  }
}
