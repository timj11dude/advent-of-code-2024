package day01

object solution {
    val example1 = """3   4
4   3
2   5
1   3
3   9
3   3""".trimMargin()

    fun solve(input: List<String>): Int {
        val parsed = input.map{ line  ->
            line.split(" ")
                .map { it.trim() }
                .filterNot { it.isNullOrBlank() }
                .let { (first, second) ->
                    first.toInt() to second.toInt()
                }
        }
        val firstList = parsed.map(Pair<Int, Int>::first)
        val secondList = parsed.map(Pair<Int, Int>::second).groupingBy { it }.eachCount()
        val result = firstList.map { f -> f * (secondList[f] ?: 0) }
        return result.sum()
    }

    @JvmStatic
    fun main(args: Array<String>) {
        println(solve(example1.split("\n")))
        println(solve(this::class.java.getResource("input").readText().split("\n")))
    }
}