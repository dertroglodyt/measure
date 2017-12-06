package de.hdc.kotlin.measure

import com.danneu.result.*
import org.junit.*
import de.hdc.kotlin.measure.Prefix.*
import org.junit.*
import org.junit.Assert.*
import com.danneu.result.getOrElse


class MeasureUnitTest {

  @Test
  fun main() {
    // test equals()
    assertFalse(GRAM.equals(Unit))
    assertTrue(GRAM.equals(GRAM))
    assertFalse(GRAM.equals(KILO_GRAM))
    assertTrue(KILO_GRAM.equals(MeasureUnit(KILO, DU_GRAM)))

    assertTrue(KILO_GRAM.equals(MeasureUnit(KILO, DU_COMBINED(Quantity(0, 0, 0, 0, 1, 0, 0)))))
    assertFalse(KILO_GRAM.equals(MeasureUnit(NONE, DU_COMBINED(Quantity(0, 0, 0, 0, 1, 0, 0)))))

    assertTrue(MeasureUnit(KILO, DU_COMBINED(Quantity(0, 0, 0, 0, 1, 0, 0))).equals(KILO_GRAM))
    assertFalse(MeasureUnit(NONE, DU_COMBINED(Quantity(0, 0, 0, 0, 1, 0, 0))).equals(KILO_GRAM))

    assertTrue(MeasureUnit(KILO, DU_COMBINED(Quantity(0, -2, 0, 0, 1, 0, 0)))
        .equals(MeasureUnit(KILO, DU_COMBINED(Quantity(0, -2, 0, 0, 1, 0, 0)))))

    // Test isEquivalentTo()
    assertFalse(GRAM.isEquivalentTo(METER))

    assertTrue(GRAM.isEquivalentTo(KILO_GRAM))
    assertTrue(METER.isEquivalentTo(AU))

    assertTrue(MeasureUnit(KILO, DU_COMBINED(Quantity(0, -2, 0, 0, 1, 0, 0)))
        .isEquivalentTo(MeasureUnit(KILO, DU_COMBINED(Quantity(0, -2, 0, 0, 1, 0, 0)))))


    // Test valueOf()
    assertEquals(Unit, measureUnitFrom("zzz").getOrElse(Unit))
    assertEquals(UNITLESS, measureUnitFrom("").getOrElse(Unit))
    assertEquals(GRAM, measureUnitFrom("g").getOrElse(Unit))
    assertEquals(KILO_GRAM, measureUnitFrom("kg").getOrElse(Unit))
    assertEquals(AU, measureUnitFrom("AU").getOrElse(Unit))
    assertEquals(GRADE_CELSIUS, measureUnitFrom("°C").getOrElse(Unit))
  }
}
