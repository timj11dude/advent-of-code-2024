package utils

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class LineTest {

    @Test
    fun testFlatLine() {
        val vector = Vector2D(1,1)
        val line = Line(vector, Coordinates(2,1))
        assertEquals(-1, line.yIntercept)
        assertEquals(1, line.xIntercept)
    }
}