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

    private fun findStartPosition(matrix: Matrix<Char>) = matrix.indices.find { coords -> matrix[coords] == '^' }!!

    private fun solve(input: List<String>): Int {
        val grid = Matrix(input.map(String::toList))
        val start = findStartPosition(grid)
        val distanceTraveled = vectorSequence.runningFold(start) { coords, vector ->
            grid.getIteratorFromVectorWithIndex(coords, vector)
                .asSequence()
                .takeWhile { (_, v) ->
                    v != OBSTACLE
                }.last().first
        }
            .zipWithNext()
            .takeWhile { (a, _) -> !a.isOnEdge }
            .flatMap { (a, b) -> (minOf(a.x,b.x)..maxOf(a.x,b.x)).flatMap { x ->
                (minOf(a.y,b.y)..maxOf(a.y, b.y)).map { y ->
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