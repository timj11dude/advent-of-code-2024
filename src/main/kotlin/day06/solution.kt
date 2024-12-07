package day06

import utils.*
import java.lang.IllegalArgumentException

private typealias Coords = Pair<Int,Int>
private val Coords.x get() = first
private val Coords.y get() = second

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

    private val startVector = Vector2D.Companion.cardinal.BT
    private val vectorSequence = generateSequence(startVector, ::getNextVector)
    private fun getNextVector(current: Vector2D) = when(current) {
        Vector2D.Companion.cardinal.BT -> Vector2D.Companion.cardinal.LR
        Vector2D.Companion.cardinal.LR -> Vector2D.Companion.cardinal.TB
        Vector2D.Companion.cardinal.TB -> Vector2D.Companion.cardinal.RL
        Vector2D.Companion.cardinal.RL -> Vector2D.Companion.cardinal.BT
        else -> throw IllegalArgumentException("not valid vector")
    }

    private const val OBSTACLE = '#'

    private fun findStartPosition(matrix: Matrix<Char>) = matrix.indices.find { (x, y) -> matrix[x,y] == '^' }!!

    private fun Matrix<Char>.isInLoop(visitedCoords: List<Coords>, startVector: Vector2D, newObstacle: Coords) = (vectorSequence.dropWhile { it != startVector }).runningFold(visitedCoords.last()) { (x, y), vector ->
        getIteratorFromVectorWithIndex(x,y, vector).asSequence()
            .takeWhile { (coords, v) -> v != OBSTACLE  && coords != newObstacle }
            .last().first
    }.let { seq ->
        seq.runningFold(visitedCoords.dropLast(1).toSet()) { previous, current ->
            previous + current
        }
            .zipWithNext()
            .takeWhile { (a, b) -> a.size != b.size }
            .last().let { (_, b) -> isNotOnEdge(b.last()) }
    }

    private fun <T> Matrix<T>.isNotOnEdge(coords: Coords) = coords.let { (x, y) -> !(x == 0 || y == 0 || x == maxX || y == maxY) }

    private fun solve(input: List<String>): Int {
        val grid = Matrix(input.map(String::toList))
        val start = findStartPosition(grid)
        val distanceTraveled = vectorSequence.drop(1).runningFold<Vector2D, Pair<Pair<Int, Int>, Vector2D>>(start to startVector) { (coords, _) , vector ->
            val x = grid.getIteratorFromVectorWithIndex(coords.x, coords.y, vector)
                .asSequence()
                .takeWhile { (_, v) ->
                    v != OBSTACLE
                }.last()
            (x.first to getNextVector(vector))
        }
            .let {seq ->
                seq.runningFold(emptyList<Pair<Coords,Vector2D>>()) { previous, current -> previous + current }
                    .filter { it.size >= 2 }
                    .map { visitedCoords ->
                        visitedCoords
                    }

            }
        return distanceTraveled.count()
    }

    @JvmStatic
    fun main(args: Array<String>) {
        println(solve(example1.lines()))
        println(solve(this.getPuzzleInput()))
    }
}