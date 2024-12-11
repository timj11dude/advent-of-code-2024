package utils

data class Vector2D(val x: Int, val y: Int) {

    operator fun not() = Vector2D(-x, -y)
    operator fun plus(other: Vector2D) = Vector2D(x + other.x, y + other.y)
    operator fun minus(other: Vector2D) = Vector2D(x - other.x, y - other.y)
    operator fun times(other: Vector2D) = Vector2D(x * other.x, y * other.y)

    companion object {
        object cardinal {
            // Basic cardinal directions
            val LR = Vector2D(1, 0)
            val RL = Vector2D(-1, 0)
            val TB = Vector2D(0, 1)
            val BT = Vector2D(0, -1)
        }
        object diagonal {
            val TLBR = Vector2D(1,-1)
            val TRBL = Vector2D(-1,-1)
            val BLTR = Vector2D(1,1)
            val BRTL = Vector2D(-1,1)
        }
    }
}

fun Vector2D.asCoordinates(): Coordinates = this.x to this.y