fun main() {

    fun extractCount(s: String): Int {
        return s.replace("[^0-9]".toRegex(), "").toInt()
    }

    fun extractColor(s: String): Int {
        return if (s.contains("blue")) 0
        else if(s.contains("red")) 1
        else 2
    }

    fun part1(input: List<String>): Int {
        var ret = 0

        for (id in 1 .. input.size) {
            val game = input[id - 1].split(":")

            var valid = true
            for (t in game[1].split(";")) {
                var colors = IntArray(3)
                for (entry in t.split(",")) {
                    val count = extractCount(entry.trim())
                    val color = extractColor(entry.trim())

                    colors[color] += count

                    if (colors[0] > 14) {
                        valid = false
                        break
                    } else if (colors[1] > 12) {
                        valid = false
                        break
                    } else if (colors[2] > 13) {
                        valid = false
                        break
                    }
                }
            }

            if (valid) {
                ret += id
            }
        }

        return ret
    }

    fun part2(input: List<String>): Long {
        var ret = 0L
        for (id in 1 .. input.size) {
            val game = input[id - 1].split(":")

            val colors = IntArray(3)
            for (t in game[1].split(";")) {
                for (entry in t.split(",")) {
                    val count = extractCount(entry.trim())
                    val color = extractColor(entry.trim())

                    colors[color] = maxOf(colors[color], count)
                }
            }

            var t = 1
            for (i in 0 until colors.size) {
                if (colors[i] == 0) continue
                t *= colors[i]
            }

            ret += t.toLong()
        }

        return ret
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part2(testInput) == 2286L)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
