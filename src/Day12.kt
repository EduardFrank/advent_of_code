
import kotlin.text.StringBuilder

fun main() {

    fun hash(s: String, n: List<Int>) = "$s${n.joinToString { "-" }}"

    fun rec(s: String, n: List<Int>, dp: MutableMap<String, Long>): Long {
        val t = dp[hash(s, n)]
        if (t != null) {
            return t
        }

        fun slice(from: Int, to: Int): String {
            if (from < s.length && to <= s.length) {
                return s.substring(from, to)
            }
            return ""
        }

        if (s.isEmpty()) {
            return if (n.isEmpty()) {
                1
            } else {
                0
            }
        }

        if (n.isEmpty()) {
            return if ('#' in s) {
                0
            } else {
                1
            }
        }

        var ret = 0L

        if (s[0] in ".?") {
            ret += rec(slice(1, s.length), n, dp)
        }

        if (s[0] in "#?") {
            if ((n[0] <= s.length && "." !in slice(0, n[0])) && (n[0] == s.length || s[n[0]] != '#')) {
                ret += rec(slice(n[0] + 1, s.length), n.subList(1, n.size), dp)
            }
        }

        dp[hash(s, n)] = ret

        return ret
    }

    fun part1(lines: List<String>): Long {
        var ret = 0L;
        for (line in lines) {
            val splits = line.split(" ")
            val s = splits[0]
            val pattern = splits[1].split(",").map { it.toInt() }

            ret += rec(s, pattern, mutableMapOf())
        }

        return ret
    }

    fun part2(lines: List<String>): Long {
        var ret = 0L;
        for (line in lines) {
            val splits = line.split(" ")
            var s = splits[0]
            var pattern = splits[1].split(",").map { it.toInt() }

            var t = java.lang.StringBuilder()
            for (i in 0 until 5) {
                if (i != 0) {
                    t.append("?")
                }
                t.append(s)
            }
            s = t.toString()

            pattern = pattern + pattern + pattern + pattern + pattern;

            ret += rec(s, pattern, mutableMapOf())
        }

        return ret
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day12_test")
    check(part1(testInput) == 21L)
    check(part2(testInput) == 525152L)

    val input = readInput("Day12")
    part1(input).println()
    part2(input).println()
}
