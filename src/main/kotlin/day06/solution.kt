package day06

import utils.*

object solution {
    private val example1 = """
        ....#.....
        .........#
        ..........
        ..#.......
        .......#..
        ..........
        .#..^.....
        ........#.
        #.........
        ......#...
    """.trimIndent()

    private val vectorSequence = sequence {
        val list = listOf(
            Vector2D.Companion.cardinal.BT,
            Vector2D.Companion.cardinal.LR,
            Vector2D.Companion.cardinal.TB,
            Vector2D.Companion.cardinal.RL
        )
        while (true) {
                yieldAll(list)
            }
        }

    private const val OBSTACLE = '#'

    private fun findStartPosition(matrix: Matrix<Char>) = matrix.indices.find { (x, y) -> matrix[x,y] == '^' }!!

    private fun solve(input: List<String>): Int {
        val grid = Matrix(input.map(String::toList))
        val start = findStartPosition(grid)
        fun isNotOnEdge(coords: Pair<Int, Int>) = coords.let { (x, y) -> !(x == 0 || y == 0 || x == grid.maxX || y == grid.maxY) }
        val distanceTraveled = vectorSequence.runningFold(start) { (x, y), vector ->
            grid.getIteratorFromVectorWithIndex(x, y, vector)
                .asSequence()
                .takeWhile { (_, v) ->
                    v != OBSTACLE
                }.last().first
        }
            .zipWithNext()
            .takeWhile { (a, _) -> isNotOnEdge(a) }
            .flatMap { (a, b) -> (minOf(a.first,b.first)..maxOf(a.first,b.first)).flatMap { x ->
                (minOf(a.second,b.second)..maxOf(a.second, b.second)).map { y ->
                    x to y
                }
            } }
            .distinct()
        return distanceTraveled.count()
    }

    @JvmStatic
    fun main(args: Array<String>) {
        println(solve(example1.lines()))
        println(solve(this.getPuzzleInput()))
    }
}