fun main() {

    fun part1(input: List<String>): Int {
        var cmds = input[0]

        var G = mutableMapOf<String, Array<String>>()
        for (i in 2 until input.size) {
            val splits = input[i].split("=")

            val key = splits[0].trim()
            val parts = splits[1].split(",")

            val a = parts[0].trim().replace("(", "").replace(")", "")
            val b = parts[1].trim().replace("(", "").replace(")", "")

            G.put(key, arrayOf(a, b))
        }

        var ret = 0
        var node = "AAA"
        var i = 0
        while (true) {
            val n = G[node]!!
            val cmd = cmds[i % cmds.length]

            val next = if (cmd == 'L') n[0] else n[1]
            ret++

            if (next == "ZZZ") {
                break
            }

            node = next

            i++
        }

        return ret
    }

    fun part2(input: List<String>): Long {
        var cmds = input[0]

        var nodes = mutableListOf<String>()
        var cycle = mutableListOf<Long>()
        var G = mutableMapOf<String, Array<String>>()
        for (i in 2 until input.size) {
            val splits = input[i].split("=")

            val key = splits[0].trim()
            val parts = splits[1].split(",")

            val a = parts[0].trim().replace("(", "").replace(")", "")
            val b = parts[1].trim().replace("(", "").replace(")", "")

            G.put(key, arrayOf(a, b))

            if (key[key.length - 1] == 'A') {
                nodes.add(key)
                cycle.add(0L)
            }
        }

        var ret = 0
        var i = 0
        var foundCycles = 0
        while (true) {
            ret++

            val newNodes = mutableListOf<String>()
            for (k in 0 until nodes.size) {
                val node = nodes[k]
                val n = G[node]!!
                val cmd = cmds[i % cmds.length]

                val next = if (cmd == 'L') n[0] else n[1]

                newNodes.add(next)

                if (cycle[k] == 0L && next[next.length - 1] == 'Z') {
                    cycle[k] = i + 1L
                    foundCycles++
                }
            }

            nodes = newNodes

            if (foundCycles >= nodes.size) {
                break
            }

            i++
        }

        fun gcd(a: Long, b: Long): Long {
            return if (b == 0L) a else gcd(b, a % b)
        }

        fun lcm(a: Long, b: Long): Long {
            return a * b / gcd(a, b)
        }

        fun findLcmOfList(numbers: List<Long>): Long {
            var result = numbers[0]
            for (i in 1 until numbers.size) {
                result = lcm(result, numbers[i])
            }

            return result
        }

        return findLcmOfList(cycle.map { it.toLong() })
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
//    check(part1(testInput) == 6)
    check(part2(testInput) == 6L)

    val input = readInput("Day08")
 //   part1(input).println()
    part2(input).println()
}
