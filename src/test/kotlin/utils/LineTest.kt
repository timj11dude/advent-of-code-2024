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

    @Test
    fun testIntersect() {
        val vector1 = Vector2D(2,1)
        val line1 = Line(vector1)

        assertEquals(Coordinates(4,2), line1.intersectPoint(Line(Vector2D(1, 2), Coordinates(5, 4))))
        assertEquals(Coordinates(6,3), line1.intersectPoint(Line(Vector2D(0, 2), Coordinates(6, 100))))
        assertEquals(Coordinates.ZERO, line1.intersectPoint(line1))

        //maybe this should fail? (as vector step size, won't really land on intersect
        assertEquals(Coordinates(4,2), line1.intersectPoint(Line(Vector2D(0, 3), Coordinates(4, 3))))

        assertNull(line1.intersectPoint(Line(Vector2D(2,1), Coordinates(5,5))))
    }
}