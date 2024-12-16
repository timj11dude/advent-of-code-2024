package utils

/**
 * An open matrix, values on which are only realised by a backing coordinate mapping said value.
 * Can provide a default value for empty spots.
 */
class OpenMatrix<V>(private val _matrix: Map<Coordinates,V> = mapOf()) {
    val maxX: Int by lazy { _matrix.keys.maxOf(Coordinates::x) }
    val minX: Int by lazy { _matrix.keys.minOf(Coordinates::x) }
    val maxY: Int by lazy { _matrix.keys.maxOf(Coordinates::y) }
    val minY: Int by lazy { _matrix.keys.minOf(Coordinates::y) }
    val area: Int by lazy { (maxX - minX) * (maxY - minY) }

    val entries: Set<Map.Entry<Coordinates, V>> get() = _matrix.entries
    val keys: Set<Coordinates> get() = _matrix.keys
    val size: Int get() = _matrix.size
    val values: Collection<V> get() = _matrix.values
    fun containsCoordinate(key: Coordinates) = _matrix.containsKey(key)
    fun containsValue(value: V) = _matrix.containsValue(value)
    operator fun get(key: Coordinates) = _matrix[key]

    /**
     * Associates the specified [value] with the specified [coordinates] in the map.
     *
     * @return a new OpenMatrix with value at [coordinates] set to [value]
     */
    fun put(coordinates: Coordinates, value: V): OpenMatrix<V> = OpenMatrix(
        _matrix + (coordinates to value)
    )
}

fun main() {
    val m = OpenMatrix<String>()
    m[Coordinates.ZERO]
}