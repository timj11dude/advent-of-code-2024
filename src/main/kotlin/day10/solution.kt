package day10

import utils.*

typealias Route<T> = List<Matrix<T>.LocalCoordinates>

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

    private fun <T> Matrix<T>.LocalCoordinates.getNeighbours() = cardinalDirections
        .mapNotNull { vector -> this + vector }
    private fun Matrix<Byte>.isGoodNeighbour(me: Matrix<Byte>.LocalCoordinates, them: Matrix<Byte>.LocalCoordinates) = this[me].inc() == this[them]
    private fun Matrix<Byte>.findAllPaths(start: Matrix<Byte>.LocalCoordinates): List<Route<Byte>> {
        fun search(route: Route<Byte>): List<Route<Byte>> {
            val last = route.last()
            if (get(last) == END) return listOf(route)
            return route.last().getNeighbours()
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
        val (startCoordinates, _) = grid.indices.partition { coords -> grid[coords] == START }
            .let { (startCoordinates, endCoordinates) -> startCoordinates to endCoordinates.filter { coords -> grid[coords] == END }}
        return startCoordinates
            .map { grid.findAllPaths(it) }
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
