package utils

import kotlin.math.abs

/**
 * FreeStanding, Coordinates without a backing Matrix.
 *
 * Prefer using [Matrix] if possible
 */
@JvmInline
value class Coordinates(private val _pair: Pair<Int, Int>) {
    constructor(x: Int, y: Int) : this(x to y)
    val x get() = _pair.first
    val y get() = _pair.second
    operator fun component1() = x
    operator fun component2() = y

    operator fun plus(vector: Vector2D): Coordinates = Coordinates(x + vector.x, y + vector.y)
    operator fun div(other: Coordinates): Vector2D = Vector2D(this.x / other.x, this.y / other.y)
    operator fun rem(other: Coordinates): Vector2D = Vector2D(this.x % other.x, this.y % other.y)

    fun vectorFrom(other: Coordinates): Vector2D = Vector2D(this.x - other.x, this.y - other.y)

    infix fun isBelow(other: Coordinates) = this.x < other.x
    infix fun isAbove(other: Coordinates) = this.x > other.x
    infix fun isLeftOf(other: Coordinates) = this.y < other.y
    infix fun isRightOf(other: Coordinates) = this.y > other.y

    override fun toString() = "Coordinates(x=$x,y=$y)"

    companion object {
        val ZERO = Coordinates(0 to 0)
    }
}

fun Coordinates.onLine(line: Line) = this == line.startCoordinates || abs(vectorFrom(line.startCoordinates).gradient) == line.vector.gradient