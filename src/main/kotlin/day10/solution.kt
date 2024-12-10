package day10

import utils.*
import utils.CoordinatesUtil.x
import utils.CoordinatesUtil.y

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

    private fun <T> Matrix<T>.getNeighbours(coords: Coordinates) = cardinalDirections.mapNotNull { (x,y) -> this.getOrNull(coords.x + x, coords.y + y) }
    private fun Matrix<Byte>.findALlPaths(start: Coordinates) {
        // do some recursive search for a valid path
        // terminate when no valid neighbor or END is found
        // return paths or count which reached END
        TODO()
    }

    private fun solve(input: List<String>): Long {
        val grid = Matrix(input.map { it.toList().map { (it.code - '0'.code).toByte() } })
        val (startCoordinates, endCoordinates) = grid.indices.partition { (x,y) -> grid[x,y] == START }
            .let { (startCoordinates, endCoordinates) -> startCoordinates to endCoordinates.filter { (x,y) -> grid[x,y] == END }}
        TODO()
    }

    @JvmStatic
    fun main(args: Array<String>) {
        println(solve(example1.lines()))
        println(solve(example2.lines()))
        println(solve(getPuzzleInput()))
    }
}
