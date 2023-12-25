
fun main() {

    var count = mutableMapOf<String, Int>()

    fun bfs(edges: MutableMap<String, MutableList<String>>, start: String, end: String) {
        var V = mutableSetOf<String>()

        var queue = mutableListOf(start to emptyList<String>())

        while (queue.isNotEmpty()) {
            val (u, p) = queue.removeFirst()
            if (u == end) {
                for (pp in p) {
                    count[pp] = count.getOrDefault(pp, 0) + 1
                }
                break
            }

            if (V.contains(u)) continue
            V.add(u)

            for (vs in edges.getOrDefault(u, emptyList())) {
                if (V.contains(vs)) continue

                val u1 = if (u < vs) u else vs
                val u2 = if (u < vs) vs else u

                val key = "$u1|$u2"
                queue.add(vs to p + key)
            }
        }
    }

    fun dfs(edges: MutableMap<String, MutableList<String>>, u: String, V: MutableSet<String>, group: MutableSet<String>) {
        var queue = mutableListOf(u)

        while (queue.isNotEmpty()) {
            val v = queue.removeFirst()

            if (V.contains(v)) continue
            V.add(v)

            group.add(v)

            for (vs in edges.getOrDefault(v, emptyList())) {
                if (V.contains(vs)) continue
                queue.add(vs)
            }
        }
    }

    fun part1(input: List<String>): Int {
        var edges = mutableMapOf<String, MutableList<String>>()
        var vertices = mutableSetOf<String>()
        for (line in input) {
            val splits = line.split(":")
            val u = splits[0].trim()
            val vs = splits[1].trim().split(" ").map { it }

            vertices.add(u)

            for (v in vs) {
                vertices.add(v)

                var t = edges[u]
                if (t == null) {
                    t = mutableListOf()
                }
                t.add(v)
                edges[u] = t

                t = edges[v]
                if (t == null) {
                    t = mutableListOf()
                }
                t.add(u)
                edges[v] = t
            }
        }

        for (i in 0 until 10000) {
            val u = vertices.random()
            val v = vertices.random()

            bfs(edges, u, v)
        }

        var top = count.entries.sortedByDescending { it.value }.take(3)

        top.forEach {
            val (u, v) = it.key.split("|")
            edges[u]?.remove(v)
            edges[v]?.remove(u)
        }

        var groups = mutableListOf<MutableSet<String>>()
        var V = mutableSetOf<String>()
        for (v in vertices) {
            if (!V.contains(v)) {
                val group = mutableSetOf<String>()
                dfs(edges, v, V, group)

                groups.add(group)
            }
        }

        return groups[0].size * groups[1].size
    }


    fun part2(input: List<String>): Int {
        var ret = 0

        return ret
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day25_test")
//    check(part1(testInput) == 54)
 //   check(part2(testInput) == 47L)

    val input = readInput("Day25")
    part1(input).println()
    part2(input).println()
}
