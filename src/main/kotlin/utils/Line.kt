package utils

/**
 * Describes a line on a 2D plain, by default through 0,0
 */
data class Line(val vector: Vector2D, val startCoordinates: Coordinates = Coordinates.ZERO) {

    /**
     * The x-axis intercept when y=0
     *
     * Is null if intercept point is not whole integer coordinate
     */
    val xIntercept: Int? by lazy {
        (startCoordinates.x - ((startCoordinates.y.toDouble() / vector.y) * vector.x)).takeIf { it % 1 == 0.0 }?.toInt()
    }

    /**
     * The y-axis intercept when x=0
     *
     * Is null if intercept point is not whole integer coordinate
     */
    val yIntercept: Int? by lazy {
        (startCoordinates.y - (m * startCoordinates.x)).takeIf { it % 1 == 0.0 }?.toInt()
    }

    /**
     * Line gradient
     */
    private val m: Double get() = vector.gradient

}


/**
 * Find the point at which these points+vectors cross if at all
 */
fun Line.findIntersectPoint(other: Line): Coordinates? {
    TODO()
}
