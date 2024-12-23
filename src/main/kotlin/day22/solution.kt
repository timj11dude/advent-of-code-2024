package day22

import kotlinx.coroutines.*
import utils.*

object solution {
    private val example1 = """
        1
        2
        3
        2024
    """.trimIndent()

    fun pseduoRandom(n: Long): Long {
        fun mix(f: Long, s: Long) = f xor s
        fun prune(f: Long) = f % 16777216
        fun step2(x: Long) = prune(mix(x, (x.toDouble() / 32).toLong() ))
        fun step3(x: Long) = prune(mix(x, x * 2048 ))
        return step3(step2(prune(mix(n, n * 64))))
    }

    private val changeRange = -9..9
    private val possibleSequences = changeRange.flatMap { f1 ->
        changeRange.filter { it in ((-9+(f1*-1))..(9+(f1*-1))) }.flatMap { f2 ->
            changeRange.filter { it in ((-9+(f2*-1))..(9+(f2*-1))) }.flatMap { f3 ->
                changeRange.filter { it in ((-9+(f3*-1))..(9+(f3*-1))) }.map { f4 ->
                    listOf(f1.toLong(), f2.toLong(), f3.toLong(), f4.toLong())
                }
            }
        }
    }

    fun getPrice(num: Long) = num % 10
    fun getChange(f: Long, s: Long) = s - f

    private fun solve(input: List<String>): Long {
        val monkeySecrets: List<List<Pair<Long,List<Long>>>> = input
            .map(String::toLong)
            .map { n ->
                generateSequence(n, ::pseduoRandom)
                    .take(2001)
                    .map(::getPrice)
                    .windowed(5, 1) { seq ->
                        seq.last() to seq.zipWithNext { a, b -> getChange(a,b) }
                    }
                    .toList()
            }

        return runBlocking {
            val searches = possibleSequences.map { seq ->
                async(Dispatchers.Default) {
                    monkeySecrets.sumOf { monkey ->
                        monkey.firstOrNull { (_, pSeq) ->
                            pSeq == seq
                        }?.first ?: 0L
                    }
                }
            }
            searches.awaitAll().max()
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        println("Number of seq: ${possibleSequences.size}")
//        println(solve(example1.lines()))
//        println(solve(getPuzzleInput()))
    }
}
