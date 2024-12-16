package utils

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class CoordinatesTest {

    @Test
    fun testOnLine() {
        val line = Line(Vector2D(1,2))
        assertTrue(Coordinates(0, 0).onLine(line))
        assertTrue(Coordinates(2, 4).onLine(line))
        assertTrue(Coordinates(-1, -2).onLine(line))

        assertFalse(Coordinates(0, -2).onLine(line))
        assertFalse(Coordinates(0, 2).onLine(line))
        assertFalse(Coordinates(2, 2).onLine(line))
        assertFalse(Coordinates(-2, 2).onLine(line))
    }

}