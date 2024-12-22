package utils

/**
 * 2D Matrix
 *
 * @see indices
 */
data class Matrix<T>(private val _matrix: List<List<T>>) {

    /**
     * Provides a list of all Coordinates in [Matrix]
     */
    val indices: List<LocalCoordinates>
        get() = _matrix.indices.flatMap { y ->
            _matrix.first().indices.map { x ->
                LocalCoordinates(
                    x,
                    y
                )
            }
        }
    val maxX by lazy { _matrix.first().size - 1 }
    val maxY by lazy { _matrix.size - 1 }

    /**
     * N.B. Guaranteed to return item
     */
    operator fun get(coordinates: LocalCoordinates) = this._matrix[coordinates.y][coordinates.x]
    fun getOrNull(x: Int, y: Int): T? = _matrix.getOrNull(y)?.getOrNull(x)
    operator fun contains(item: T) = _matrix.any { row -> row.contains(item) }

    fun prettyPrint() {
        _matrix.joinToString("\n") { row -> row.joinToString("") }
    }

    inner class LocalCoordinates internal constructor(val x: Int, val y: Int) {
        init {
            require(isWithinBounds(x, y)) { "Cannot construct LocalCoordinate outside of bounds." }
        }

        val isOnEdge: Boolean by lazy { x == 0 || y == 0 || x == maxX || y == maxY }

        operator fun plus(vector: Vector2D) = (x + vector.x to y + vector.y)
            .takeIf { (_x, _y) -> isWithinBounds(_x, _y) }
            ?.let { (_x, _y) -> LocalCoordinates(_x, _y) }

        operator fun minus(vector: Vector2D) = (x - vector.x to y - vector.y)
            .takeIf { (_x, _y) -> isWithinBounds(_x, _y) }
            ?.let { (_x, _y) -> LocalCoordinates(_x, _y) }

        private fun isWithinBounds(x: Int, y: Int) = x in 0..maxX && y in 0..maxY
    }
}

/**
 * On [this] Matrix, given a set of start coordinates and vector, returns an iterator for all values within bounds.
 */
fun <T> Matrix<T>.getIteratorFromVectorWithIndex(
    startCoordinates: Matrix<T>.LocalCoordinates,
    vector: Vector2D
): Iterator<Pair<Matrix<T>.LocalCoordinates, T>> = iterator {
    yield(startCoordinates to get(startCoordinates))
    var nextCoordinates = startCoordinates + vector
    while (nextCoordinates != null) {
        yield(nextCoordinates to get(nextCoordinates))
        nextCoordinates += vector
    }
}

/**
 * On [this] Matrix, given a set of [startCoordinates] and [vector], returns an iterator for all coordinates and values within bounds.
 */
fun <T> Matrix<T>.getIteratorFromVector(startCoordinates: Matrix<T>.LocalCoordinates, vector: Vector2D): Iterator<T> =
    getIteratorFromVectorWithIndex(startCoordinates, vector).asSequence().map { it.second }.iterator()

/**
 * Associates each [view] item to the [T] in [this] Matrix
 * @param T Matrix
 * @param view Vectors relative to given [x] and [y] on which to retrieve value
 * @return Map of local coordinates to their corresponding value in this Matrix
 */
fun <T> Matrix<T>.associateView(coordinates: Matrix<T>.LocalCoordinates, view: List<Vector2D>) = view
    .mapNotNull(coordinates::plus)
    .associateWith(::get)


/**
 * Produces a vector from [this] to [other]
 */
operator fun <T> Matrix<T>.LocalCoordinates.minus(other: Matrix<T>.LocalCoordinates): Vector2D =
    Vector2D(other.x - x, other.y - y)

fun <T> Matrix<T>.LocalCoordinates.getCardinalNeighbours() = Vector2D.Companion.cardinal.all
    .mapNotNull { vector -> this + vector }