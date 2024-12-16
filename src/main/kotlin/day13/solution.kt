package day13

import utils.*
import kotlin.math.min

object solution {
    private val example1 = """
        Button A: X+94, Y+34
        Button B: X+22, Y+67
        Prize: X=8400, Y=5400

        Button A: X+26, Y+66
        Button B: X+67, Y+21
        Prize: X=12748, Y=12176

        Button A: X+17, Y+86
        Button B: X+84, Y+37
        Prize: X=7870, Y=6450

        Button A: X+69, Y+23
        Button B: X+27, Y+71
        Prize: X=18641, Y=10279
    """.trimIndent()

    private val START_POS: Coordinates = Coordinates.ZERO

    private val buttonRegex = """Button (?:A|B): X\+(\d+), Y\+(\d+)""".toRegex()
    private fun String.mapButton(): Vector2D = buttonRegex.find(this.trim())
        ?.groupValues
        ?.let { (_, x, y) -> Vector2D(x.toInt(), y.toInt()) }
        ?: throw IllegalArgumentException("this is not a button: $this")

    private val prizeRegex = """Prize: X=(\d+), Y=(\d+)""".toRegex()
    private fun String.mapPrize(): Coordinates = prizeRegex.find(this)
        ?.groupValues
        ?.let { (_, x, y) -> Coordinates(x.toInt(), y.toInt()) }
        ?: throw IllegalArgumentException("this is not a prize: $this")

    private data class Game(val buttonA: Vector2D, val buttonB: Vector2D, val prizeCoordinates: Coordinates)

    private fun solve(input: List<String>): Long {
        val games = input
            .filterNot(String::isNullOrBlank)
            .windowed(3, 3, false) { (buttonA, buttonB, prize) ->
                Game(
                    buttonA.mapButton(),
                    buttonB.mapButton(),
                    prize.mapPrize()
                )
            }
        return games.fold(0L) { acc, game ->
            if (game.buttonB.scalar * 3 > game.buttonA.scalar) Line(game.buttonB).intersectPoint(
                Line(game.buttonA, game.prizeCoordinates)
            )?.let { intersectPoint ->
                val aPresses = intersectPoint.vectorFrom(game.prizeCoordinates).scalar / game.buttonA.scalar
                val bPresses = intersectPoint.vectorFrom(Coordinates.ZERO).scalar / game.buttonB.scalar
                acc + (aPresses.toLong() * 3) + bPresses.toLong()
            } ?: acc
            else Line(game.buttonA).intersectPoint(
                Line(game.buttonB, game.prizeCoordinates)
            )?.let { intersectPoint ->
                val aPresses = intersectPoint.vectorFrom(Coordinates.ZERO).scalar / game.buttonA.scalar
                val bPresses = intersectPoint.vectorFrom(game.prizeCoordinates).scalar / game.buttonB.scalar
                acc + (aPresses.toLong() * 3) + bPresses.toLong()
            } ?: acc
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        println(solve(example1.lines()))
        println(solve(getPuzzleInput()))
    }
}
