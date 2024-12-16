package utils

/**
 * Describes a line on a 2D plain, by default through 0,0
 */
data class Line(val vector: Vector2D, val startCoordinates: Coordinates = Coordinates.ZERO) {
    /**
     * The x-axis intercept when y=0
     */
    val xIntercept: Double by lazy { (startCoordinates.x - ((startCoordinates.y.toDouble() / vector.y) * vector.x)) }
    /**
     * The y-axis intercept when x=0
     */
    val yIntercept: Double by lazy { (startCoordinates.y - (vector.gradient * startCoordinates.x)) }

    fun intersectPoint(other: Line): Coordinates? {
        if (startCoordinates == other.startCoordinates) return startCoordinates
        val roughVector = other.startCoordinates.vectorFrom(startCoordinates)
        val seqVector = if ((roughVector.y <= 0) == (vector.y <= 0)) vector else !vector
        return generateSequence(startCoordinates) { c -> c + seqVector }
            .takeWhile { (other.startCoordinates.vectorFrom(it).y <= 0) == (roughVector.y <= 0) }
            .firstOrNull { it.onLine(other) }
    }
}