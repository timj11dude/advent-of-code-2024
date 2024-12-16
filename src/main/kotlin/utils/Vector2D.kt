package utils

import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sqrt

/**
 * A measure of size/distance, without consideration for direction.
 * @see Vector2D
 */
typealias Scalar = Double

/**
 * A 2D Vector that describes a direction scalar, that can be applied to a [Matrix]
 */
data class Vector2D(val x: Int, val y: Int) {

    operator fun not() = Vector2D(-x, -y)
    operator fun plus(other: Vector2D) = Vector2D(x + other.x, y + other.y)
    operator fun minus(other: Vector2D) = Vector2D(x - other.x, y - other.y)
    operator fun times(other: Vector2D) = Vector2D(x * other.x, y * other.y)

    val scalar: Scalar by lazy { sqrt(abs(x).toDouble().pow(2.0) + abs(y).toDouble().pow(2.0)) }
    val gradient: Double by lazy { y.toDouble() / x }

    /**
     * Scales this vector by k in the following ways:
     * - k > 1 stretch this vector
     * - 0 < k < 1 shrink this vector
     * - k = 0 the vector is zeroed
     * - k < 0 the vector is flipped and scaled
     *
     * Rounds axis values to nearest Int **(may result in different vector!)**
     */
    fun scaleTo(k: Double): Vector2D = when {
        k == 0.0 -> ZERO
        else -> Vector2D((x * k).roundToInt(), (y * k).roundToInt())
    }

    companion object {
        val ZERO = Vector2D(0, 0)

        object cardinal {
            // Basic cardinal directions
            val LR = Vector2D(1, 0)
            val RL = Vector2D(-1, 0)
            val TB = Vector2D(0, 1)
            val BT = Vector2D(0, -1)
            val all = listOf(LR, RL, TB, BT)
        }

        object diagonal {
            val TLBR = Vector2D(1, -1)
            val TRBL = Vector2D(-1, -1)
            val BLTR = Vector2D(1, 1)
            val BRTL = Vector2D(-1, 1)
        }
    }
}
