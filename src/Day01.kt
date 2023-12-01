fun main() {
    val digits = listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
        .mapIndexed { i, s -> s to (i + 1) }
        .toMap()

    fun part1(input: List<String>): Int {
        return input
            .map { it.toCharArray().filter { it.isDigit() } }
            .sumOf {
                10 * (it.first().digitToInt()) + (it.last().digitToInt())
            }
    }

    fun part2(input: List<String>): Int {
        return input
            .sumOf { s ->

                val digitNums =
                    s.indices
                        .filter  { s[it].isDigit() }
                        .map     { s[it].digitToInt() to it }

                val textNums = buildList {
                    var idx: Int
                    for (d in digits) {
                        idx = s.indexOf(d.key)
                        while (idx != -1) {
                            add(d.value to idx)
                            idx = s.indexOf(d.key, idx + 1)
                        }
                    }
                }

                val nums = (digitNums + textNums)
                    .sortedBy { it.second }

                10 * nums.first().first + nums.last().first
            }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part2(testInput) == 281)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
