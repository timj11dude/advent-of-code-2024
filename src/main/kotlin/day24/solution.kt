package day24

import utils.split

data class Signal(val signal: String, val active: Boolean)
sealed interface Gate {
    val left: String
    val right: String
    val output: String
    class AND(override val left: String, override val right: String, override val output: String) : Gate
    class OR(override val left: String, override val right: String, override val output: String) : Gate
    class XOR(override val left: String, override val right: String, override val output: String) : Gate
}

object solution {
    private val example1 = """
        x00: 1
        x01: 0
        x02: 1
        x03: 1
        x04: 0
        y00: 1
        y01: 1
        y02: 1
        y03: 1
        y04: 1

        ntg XOR fgs -> mjb
        y02 OR x01 -> tnw
        kwq OR kpj -> z05
        x00 OR x03 -> fst
        tgd XOR rvg -> z01
        vdt OR tnw -> bfw
        bfw AND frj -> z10
        ffh OR nrd -> bqk
        y00 AND y03 -> djm
        y03 OR y00 -> psh
        bqk OR frj -> z08
        tnw OR fst -> frj
        gnj AND tgd -> z11
        bfw XOR mjb -> z00
        x03 OR x00 -> vdt
        gnj AND wpb -> z02
        x04 AND y00 -> kjc
        djm OR pbm -> qhw
        nrd AND vdt -> hwm
        kjc AND fst -> rvg
        y04 OR y02 -> fgs
        y01 AND x02 -> pbm
        ntg OR kjc -> kwq
        psh XOR fgs -> tgd
        qhw XOR tgd -> z09
        pbm OR djm -> kpj
        x03 XOR y03 -> ffh
        x00 XOR y04 -> ntg
        bfw OR bqk -> z06
        nrd XOR fgs -> wpb
        frj XOR qhw -> z04
        bqk OR frj -> z07
        y03 OR x01 -> nrd
        hwm AND bqk -> z03
        tgd XOR rvg -> z12
        tnw OR pbm -> gnj
    """.trimIndent()

    private fun parseSignal(signal: String): Signal = signal.split(":").map(String::trim).let { (s,a) ->
        s to when (a) {
            "1" -> true
            "0" -> false
            else -> throw IllegalArgumentException("Unexpected signal $s: $a")
        }
    }.let { (s,a) -> Signal(s,a) }
    private fun parseGate(input: String): Gate = input.split(" ").let { (l, o, r) ->
        when (o) {
            "AND" -> Gate.AND(l, r)
            "OR" -> Gate.OR(l, r)
            "XOR" -> Gate.XOR(l, r)
            else -> throw IllegalArgumentException("Unexpected operator: $o")
        }
    }

    private fun solve(input: List<String>): Any {
        val (signalInputs, gates) = input.split(String::isEmpty)
            .let { (f, s) -> f.map(::parseSignal) to s.map(::parseGate) }

        val finalGates = gates.filter { it.output.startsWith("z") }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        println(solve(example1.lines()))
//        println(solve(getPuzzleInput()))
    }
}
