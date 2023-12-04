fun main() {

    fun parseNums(s: String): List<Int> {
        val splits = s.split(" ").filter { it.isNotEmpty() }
        return splits.map { it.toInt() }
    }

    fun part1(input: List<String>): Int {
        var ret = 0

        for (game in input) {
            var score = 0
            val t1 = game.split(": ")
            val tt = t1[1].split("|")

            val wNums = parseNums(tt[0]).toSet()
            val hNums = parseNums(tt[1]).toSet()

            for (num in hNums) {
                if (wNums.contains(num)) {
                    if (score == 0) {
                        score = 1
                    } else {
                        score *= 2
                    }
                }
            }

            ret += score
        }

        return ret
    }

    fun part2(input: List<String>): Int {
        var ret = 0
        val scores = IntArray(187)
        val matches = IntArray(187)
        val V = IntArray(1000)

        for (i in 0 until input.size) {
            val game = input[i]
            var score = 0
            val t1 = game.split(": ")
            val tt = t1[1].split("|")

            val wNums = parseNums(tt[0]).toSet()
            val hNums = parseNums(tt[1]).toSet()

            for (num in hNums) {
                if (wNums.contains(num)) {
                    score++
                }
            }

            if (score > 0) {
                matches[i+ 1] = score
                scores[i + 1]++
            } else {
                scores[i + 1] = 1
            }

            ret += score
        }

        for (i in 1 until matches.size) {
            for (k in 1 .. matches[i]) {
                scores[i + k] += scores[i]
            }
        }

        return scores.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part2(testInput) == 30)

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
