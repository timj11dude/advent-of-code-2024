package utils

/**
 * Basic matrix holder, with convenience methods
 */
data class Matrix<T>(private val _matrix: List<List<T>>) {

    val indices get() = _matrix.indices.flatMap { y -> _matrix.first().indices.map { x -> x to y } }
    val maxX by lazy { _matrix.first().size - 1 }
    val maxY by lazy { _matrix.size - 1 }

    operator fun get(x: Int, y: Int) = _matrix[y][x]
    fun getOrNull(x: Int, y: Int) = _matrix.getOrNull(y)?.getOrNull(x)
    operator fun contains(item: T) = _matrix.any { row -> row.contains(item) }
}

/**
 * On a given matrix, providing a starting coordinate and vector, generate an iterator
 */
fun <T> Matrix<T>.getIteratorFromVector(_x: Int, _y: Int, vector2D: Vector2D): Iterator<T> = iterator {
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

fun <T> Matrix<T>.getIteratorFromVectorWithIndex(_x: Int, _y: Int, vector2D: Vector2D): Iterator<Pair<Pair<Int,Int>,T>> = iterator {
    var x = _x
    var y = _y
    var r = getOrNull(x,y)
    while (r != null) {
        yield((x to y) to r)
        x += vector2D.x
        y += vector2D.y
        r = getOrNull(x,y)
    }
}

/**
 * Associates each [view] item to the [T] in [this] Matrix
 * @param T Matrix
 * @param view Vectors relative to given [x] and [y] on which to retrieve value
 * @return Map of input vectors to their corresponding value in this Matrix
 */
fun <T> Matrix<T>.associateView(x: Int, y: Int, view: List<Vector2D>): Map<Vector2D, T?> = view.associateWith { vector ->
    getOrNull(x+vector.x, y+vector.y)
}