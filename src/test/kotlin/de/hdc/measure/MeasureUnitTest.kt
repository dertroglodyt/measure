package de.hdc.measure

import de.hdc.measure.Prefix.KILO
import de.hdc.measure.Prefix.NONE
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class MeasureUnitTest {

  @Test
  fun main() {
    // test equals()
    assertTrue(`°C`.equals(`°C`))
    assertFalse(K.equals(`°C`))

    assertFalse(g.equals(Unit))
    assertTrue(g.equals(g))
    assertFalse(g.equals(kg))
    assertTrue(kg.equals(MeasureUnit(KILO, "", "", SI_GRAM)))

    assertTrue(kg.equals(MeasureUnit(KILO, "", "", SI_COMBINED(Quantity(0, 0, 0, 0, 1, 0, 0)))))
    assertFalse(kg.equals(MeasureUnit(NONE, "", "", SI_COMBINED(Quantity(0, 0, 0, 0, 1, 0, 0)))))

    assertTrue(MeasureUnit(KILO, "", "", SI_COMBINED(Quantity(0, 0, 0, 0, 1, 0, 0)))
            .equals(kg))
    assertFalse(MeasureUnit(NONE, "", "", SI_COMBINED(Quantity(0, 0, 0, 0, 1, 0, 0)))
            .equals(kg))

    assertTrue(MeasureUnit(KILO, "", "", SI_COMBINED(Quantity(0, -2, 0, 0, 1, 0, 0)))
        .equals(MeasureUnit(KILO, "", "", SI_COMBINED(Quantity(0, -2, 0, 0, 1, 0, 0)))))

    // Test isEquivalentTo()
    assertFalse(g.isEquivalentTo(m))

    assertTrue(g.isEquivalentTo(kg))
    assertTrue(m.isEquivalentTo(AU))

    assertTrue(MeasureUnit(KILO, "", "", SI_COMBINED(Quantity(0, -2, 0, 0, 1, 0, 0)))
        .isEquivalentTo(MeasureUnit(KILO, "", "", SI_COMBINED(Quantity(0, -2, 0, 0, 1, 0, 0)))))


//    // Test valueOf()
//    // not recognized
//    assertEquals(Unit, measureUnitFrom("zzz").getOrElse(Unit))
//    //some common units
//    assertEquals(UNITLESS, measureUnitFrom("").getOrElse(Unit))
//    assertEquals(g, measureUnitFrom("g").getOrElse(Unit))
//    assertEquals(kg, measureUnitFrom("kg").getOrElse(Unit))
//    assertEquals(AU, measureUnitFrom("AU").getOrElse(Unit))
//    assertEquals(GRADE_CELSIUS, measureUnitFrom("°C").getOrElse(Unit))
  }
}
