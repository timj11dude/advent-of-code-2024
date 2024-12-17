package day14

import utils.*

typealias Lines = List<String>
typealias Robot = Pair<Coordinates, Vector2D>

object solution {
    private val example1 = """
        p=0,4 v=3,-3
        p=6,3 v=-1,-3
        p=10,3 v=-1,2
        p=2,0 v=2,-1
        p=0,0 v=1,3
        p=3,0 v=-2,-2
        p=7,6 v=-1,-3
        p=3,0 v=-1,-2
        p=9,3 v=2,3
        p=7,3 v=-1,2
        p=2,4 v=2,-3
        p=9,5 v=-3,-3
    """.trimIndent()
    private val example2 = """
        p=2,4 v=2,-3
    """.trimIndent()

    private fun scoreQuadrants(guards: List<Coordinates>, maxGridSize: Pair<Int, Int>): Int {
        val halfX = maxGridSize.first / 2
        val halfY = maxGridSize.second / 2
        return guards.partition { robot -> robot.x < halfX }.toList().flatMap { half ->
            half.partition { robot -> robot.y > halfY }.toList()
                .map { quarter -> quarter.filterNot { it.x == halfX || it.y == halfY } }
                .also { println("quadrants: $it") }
                .map(List<*>::count)
        }.fold(1) { acc, i -> acc * i }
    }

    private fun solve(input: Lines, maxGridSize: Pair<Int, Int>, iterations: Int): Int {
        fun conform(axis: Int, max: Int) = if (axis >= 0)
            axis % max
        else
            max + ((axis+1) % (max+1))
        return parse(input)
            .also { println("init robots: $it") }
            .map { robot ->
                (robot.first + (robot.second * iterations)).let { (x, y) ->
                    Coordinates(
                        conform(x, maxGridSize.first),
                        conform(y, maxGridSize.second)
                    )
                }
            }
            .also { println("final robots: $it") }
            .let { robots ->
                scoreQuadrants(robots, maxGridSize)
            }
    }

    private val robotRegex = """p=(-?\d+),(-?\d+) v=(-?\d+),(-?\d+)""".toRegex()
    private fun parse(input: Lines): List<Robot> = input
        .mapNotNull(robotRegex::find)
        .map(MatchResult::groupValues)
        .map { (_, x, y, vX, vY) -> Coordinates(x.toInt(), y.toInt()) to Vector2D(vX.toInt(), vY.toInt()) }

    @JvmStatic
    fun main(args: Array<String>) {
        println(solve(example1.lines(), 10 to 6, 100))
        println(solve(example2.lines(), 10 to 6, 3))
//        println(solve(getPuzzleInput(), 100 to 102))
    }
}
