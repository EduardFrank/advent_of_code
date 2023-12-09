fun main() {

    fun part1(input: List<String>): Int {
        fun reduce(input: List<Int>): List<Int> {
            val ret = mutableListOf<Int>()

            for (i in 1 until input.size) {
                ret.add(input[i] - input[i  - 1])
            }

            return ret
        }

        fun expand(input: MutableList<MutableList<Int>>): Int {
            input[input.size - 1].add(0)

            for (i in input.size - 2 downTo 0) {
                val k = input[i + 1][input[i + 1].size - 1] + input[i][input[i].size - 1]
                input[i].add(k)
            }

            val t = input[0].last()
            return t
        }

        var ret = 0
        for (input2 in input) {
            val G = mutableListOf<MutableList<Int>>()
            var t = input2.split(" ").map { it.toInt() }

            G.add(t.toMutableList())
            while (true) {
                val next = reduce(t)
                G.add(next.toMutableList())

                if (next.all { it == 0 }) {
                    break
                }

                t = next
            }

            ret += expand(G)
        }

        return ret
    }

    fun part2(input: List<String>): Long {
        fun reduce(input: List<Int>): List<Int> {
            val ret = mutableListOf<Int>()

            for (i in 1 until input.size) {
                ret.add(input[i] - input[i  - 1])
            }

            return ret
        }

        fun expand(input: MutableList<MutableList<Int>>): Int {
            input[input.size - 1].add(0, 0)

            for (i in input.size - 2 downTo 0) {
                val k = input[i].first() - input[i + 1].first()
                input[i].add(0, k)
            }

            val t = input[0].first()
            return t
        }

        var ret = 0L
        for (input2 in input) {
            val G = mutableListOf<MutableList<Int>>()
            var t = input2.split(" ").map { it.toInt() }

            G.add(t.toMutableList())
            while (true) {
                val next = reduce(t)
                G.add(next.toMutableList())

                if (next.all { it == 0 }) {
                    break
                }

                t = next
            }

            ret += expand(G)

        }

        return ret
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 114)
    check(part2(testInput) == 2L)

    val input = readInput("Day09")
    part1(input).println()
    part2(input).println()
}
