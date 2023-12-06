fun main() {

    fun part1(input: List<String>): Int {
        val times = input[0].split(":")[1].split(" ").filter { it.isNotEmpty() }.map { it.toInt() }
        val distances = input[1].split(":")[1].split(" ").filter { it.isNotEmpty() }.map { it.toInt() }

        var ret = 1

        for (k in 0 until times.size) {
            val time = times[k]
            val distance = distances[k]

            var wins = 0
            for (i in 1 .. time) {
                if ((time - i) * i > distance) {
                    wins++
                }
            }

            ret *= wins
        }

        return ret
    }

    fun part2(input: List<String>): Int {
        val time = input[0].split(":")[1].split(" ").filter { it.isNotEmpty() }.map { it.toLong() }.joinToString("").toLong()
        val distance = input[1].split(":")[1].split(" ").filter { it.isNotEmpty() }.map { it.toLong() }.joinToString("").toLong()

        var ret = 1

        var wins = 0
        for (i in 1 .. time) {
            if ((time - i) * i > distance) {
                wins++
            }
        }

        ret *= wins

        return ret
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part2(testInput) == 71503)

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}
