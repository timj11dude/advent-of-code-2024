package utils

/**
 * A shorthand for referring to Coordinates
 * @see CoordinatesUtil
 */
typealias Coordinates = Pair<Int, Int>
object CoordinatesUtil {
    val Coordinates.x get() = first
    val Coordinates.y get() = second
    operator fun Coordinates.plus(other: Coordinates) = x + other.x to y + other.y
    operator fun Coordinates.minus(other: Coordinates) = x - other.x to y - other.y
    operator fun Coordinates.times(multiplier: Int) = x * multiplier to y * multiplier
}