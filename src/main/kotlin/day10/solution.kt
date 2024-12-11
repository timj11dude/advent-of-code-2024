package day10

import utils.*
import utils.CoordinatesUtil.x
import utils.CoordinatesUtil.y

<<<<<<< HEAD
=======
typealias Route = List<Coordinates>

>>>>>>> 228dcaf (day 10 part 1)
object solution {
    private val example1 = """
        0123
        1234
        8765
        9876
    """.trimIndent()
    private val example2 = """
        89010123
        78121874
        87430965
        96549874
        45678903
        32019012
        01329801
        10456732
    """.trimIndent()

    private const val START: Byte = 0
    private const val END: Byte = 9

    private val cardinalDirections = Vector2D.Companion.cardinal.all

    private fun <T> Matrix<T>.getNeighbours(coords: Coordinates) = cardinalDirections
        .map { (x,y) -> coords.x + x to coords.y + y }
        .filter { (x,y) -> getOrNull(x, y) != null }
    private fun Matrix<Byte>.isGoodNeighbour(me: Coordinates, them: Coordinates) = this[me].inc() == this[them]
    private fun Matrix<Byte>.findALlPaths(start: Coordinates): List<Route> {
        fun search(route: Route): List<Route> {
            val last = route.last()
            if (get(last) == END) return listOf(route)
            return getNeighbours(route.last())
                .filter { neighbour ->
                    isGoodNeighbour(last, neighbour)
                }
                .flatMap { neighbour ->
                    search(route + neighbour)
                }
        }
        return search(listOf(start))
    }

    private fun solve(input: List<String>): Long {
        val grid = Matrix(input.map { it.toList().map { (it.code - '0'.code).toByte() } })
        val (startCoordinates, endCoordinates) = grid.indices.partition { (x,y) -> grid[x,y] == START }
            .let { (startCoordinates, endCoordinates) -> startCoordinates to endCoordinates.filter { (x,y) -> grid[x,y] == END }}
        return startCoordinates
            .map { grid.findALlPaths(it) }
            .sumOf { it.size }
            .toLong()
    }

    @JvmStatic
    fun main(args: Array<String>) {
        println(solve(example1.lines()))
        println(solve(example2.lines()))
        println(solve(getPuzzleInput()))
    }
}
