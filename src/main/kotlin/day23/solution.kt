package day23

import utils.*

@JvmInline
value class Id(val id: String)

typealias Nodes = Map<Id,Set<Id>>
typealias Network = Set<Id>

object solution {
    private val example1 = """
        kh-tc
        qp-kh
        de-cg
        ka-co
        yn-aq
        qp-ub
        cg-tb
        vc-aq
        tb-ka
        wh-tc
        yn-cg
        kh-ub
        ta-co
        de-co
        tc-td
        tb-wq
        wh-td
        ta-ka
        td-qp
        aq-cg
        wq-ub
        ub-vc
        de-ta
        wq-aq
        wq-vc
        wh-yn
        ka-de
        kh-ta
        co-tc
        wh-qp
        tb-vc
        td-yn
    """.trimIndent()

    /**
     * Given a set of Ids paired with connected ids, find all common connected ids.
     */
    context(Map<Id,Set<Id>>)
    private fun Set<Id>.findCommonIds(): Set<Id> = map { getValue(it) }.reduce { f, s -> f.intersect(s) }

    private fun Nodes.findSubnets(size: Int): Set<Network> {
        require(size>=0) { "Size must greater than or equal 0."}
        if (size == 0) return emptySet()
        if (size == 1) return keys.map(::setOf).toSet()
        var n = size - 2
        var networks: Set<Network> = this.entries.flatMap { (k,v) -> v.map { setOf(k,it) } }.toSet()
        while (n-- >= 1) {
            networks = networks.flatMap { network ->
                network.findCommonIds().map { newId ->
                    network + newId
                }
            }.toSet()
        }
        return networks
    }

    private fun solve(input: List<String>): Int {
        val idPairs: List<Pair<Id,Id>> = input.flatMap { it.split("-").map(::Id).let { (f,s) -> listOf(f to s, s to f)  } }
        val nodes: Nodes = idPairs.fold(emptyMap()) { acc, pair ->
            acc + (pair.first to acc.getOrDefault(pair.first, emptySet()) + pair.second)
        }
        val networks = nodes.findSubnets(3)
        return networks.count { network -> network.any { it.id.startsWith('t') } }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        println(solve(example1.lines()))
        println(solve(getPuzzleInput()))
    }
}
