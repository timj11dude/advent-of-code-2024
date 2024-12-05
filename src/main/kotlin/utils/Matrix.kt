package utils

/**
 * Basic matrix holder, with convenience methods
 */
data class Matrix<T>(private val _matrix: List<List<T>>) {

    val indicies get() = _matrix.indices.flatMap { y -> _matrix.first().indices.map { x -> x to y } }

    operator fun get(x: Int, y: Int) = _matrix[y][x]
    fun getOrNull(x: Int, y: Int) = _matrix.getOrNull(y)?.getOrNull(x)
    operator fun contains(item: T) = _matrix.any { row -> row.contains(item) }
}

/**
 * On a given matrix, providing a starting coordinate and vector, generate an iterator
 */
fun <T> Matrix<T>.getIteratorWithVector(_x: Int, _y: Int, vector2D: Vector2D): Iterator<T> = iterator {
    var x = _x
    var y = _y
    var r = getOrNull(x,y)
    while (r != null) {
        yield(r)
        x += vector2D.x
        y += vector2D.y
        r = getOrNull(x,y)
    }
}

fun <T> Matrix<T>.associateView(x: Int, y: Int, view: List<Vector2D>) = view.associateWith { vector ->
    getOrNull(x+vector.x, y+vector.y)
}