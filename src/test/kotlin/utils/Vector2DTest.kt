package utils

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class Vector2DTest {
    @Test
    fun testNot() {
        val vector2D = !Vector2D(1, 1)
        assertEquals(Vector2D(-1, -1), vector2D)
    }

    //scalar
    @Nested
    inner class ScalarTest {
        @Test
        fun testLarger() {
            assertTrue(Vector2D(5, 0).scalar < Vector2D(5, 1).scalar)
        }

        @Test
        fun testSmaller() {
            assertTrue(Vector2D(5, 0).scalar > Vector2D(3, 1).scalar)
        }

        @Test
        fun testSame() {
            assertTrue(Vector2D(5, 0).scalar == Vector2D(0, 5).scalar)
        }
    }

    //gradient
    @Nested
    inner class GradientTest {
        @Test
        fun testEqual() {
            val (actual, expected) = listOf(
                Vector2D(1, 1) to 1.0,
                Vector2D(2, 0) to 0.0,
                Vector2D(0, 2) to Double.POSITIVE_INFINITY,
                Vector2D(1, 2) to 2.0
            ).let { testCase ->
                testCase.map(Pair<Vector2D, Double>::first) to testCase.map(Pair<Vector2D, Double>::second)
            }
                .let { (actual, expected) -> actual.map { it.gradient } to expected }
                .let { (actual, expected) -> actual.toTypedArray() to expected.toTypedArray() }
            assertArrayEquals(expected, actual)
        }
    }
}