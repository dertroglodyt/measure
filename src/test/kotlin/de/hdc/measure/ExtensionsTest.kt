package de.hdc.measure

import io.kotlintest.specs.AnnotationSpec
import io.kotlintest.specs.Test
import org.junit.jupiter.api.Assertions.*

import java.lang.IllegalArgumentException

internal class ExtensionsTest: AnnotationSpec() {

  @Test
  fun format() {
    assertEquals("NaN", Double.NaN.format())

    assertEquals("3,123", 3.12345.format())

    assertEquals("-0", (-0.0).format(0))
    assertEquals("3", 3.12345.format(0))
    assertEquals("3,1", 3.12345.format(1))
    assertEquals("3,123 5", 3.12345.format(4))

    assertEquals("-3,123", (-3.12345).format())
    assertEquals("-3", (-3.12345).format(0))
    assertEquals("-3,1", (-3.12345).format(1))
    assertEquals("-3,123 5", (-3.12345).format(4))

    assertEquals("0,123", 0.12345.format())
    assertEquals("1,123", 1.12345.format())
    assertEquals("12,123", 12.12345.format())
    assertEquals("123,123", 123.12345.format())
    assertEquals("1 234,123", 1234.12345.format())
    assertEquals("12 345,123", 12345.12345.format())
    assertEquals("123 456,123", 123456.12345.format())
    assertEquals("1 234 567,123", 1234567.12345.format())
    assertEquals("12 345 678,123", 12345678.12345.format())
    assertEquals("123 456 789,123", 123456789.12345.format())

    assertEquals("-0,123", (-0.12345).format())
    assertEquals("-1,123", (-1.12345).format())
    assertEquals("-12,123", (-12.12345).format())
    assertEquals("-123,123", (-123.12345).format())
    assertEquals("-1 234,123", (-1234.12345).format())
    assertEquals("-12 345,123", (-12345.12345).format())
    assertEquals("-123 456,123", (-123456.12345).format())
    assertEquals("-1 234 567,123", (-1234567.12345).format())
    assertEquals("-12 345 678,123", (-12345678.12345).format())
    assertEquals("-123 456 789,123", (-123456789.12345).format())

    assertEquals("0,1", 0.1.format(1))
    assertEquals("0,12", 0.12.format(2))
    assertEquals("0,123", 0.123.format(3))
    assertEquals("0,123 4", 0.1234.format(4))
    assertEquals("0,123 45", 0.12345.format(5))
    assertEquals("0,123 456", 0.123456.format(6))
    assertEquals("0,123 456 7", 0.1234567.format(7))
    assertEquals("0,123 456 78", 0.12345678.format(8))
    assertEquals("0,123 456 789", 0.123456789.format(9))

    assertThrows(IllegalArgumentException().javaClass, {3.12345.format(-1)})
  }

  @Test
  fun testInvalid() {
    assertFalse(2.3.testInvalid())
    assertTrue(Double.NaN.testInvalid())
    assertTrue(Double.MAX_VALUE.testInvalid())
    assertTrue(Double.MIN_VALUE.testInvalid())
    assertTrue(Double.NEGATIVE_INFINITY.testInvalid())
    assertTrue(Double.POSITIVE_INFINITY.testInvalid())
  }

  @Test
  fun div() {
    error("not implemented")
  }

  @Test
  fun times() {
    error("not implemented")
  }
}