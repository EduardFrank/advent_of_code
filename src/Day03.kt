fun main() {

    val DX = intArrayOf(-1, -1, -1, 0, 1, 1, 1, 0)
    val DY = intArrayOf( 1, 0, -1, -1, -1, 0, 1, 1)

    fun part1(input: List<String>): Int {
        var ret = 0

        for (i in 0 until input.size) {
            var j = 0
            while (j < input[i].length) {
                if (input[i][j].isDigit()) {
                    for (k in 0 until DX.size) {
                        val dx = j + DX[k]
                        val dy = i + DY[k]

                        if (dx < 0 || dx >= input[i].length || dy < 0 || dy >= input.size)
                            continue

                        if (!input[dy][dx].isDigit() && input[dy][dx] != '.') {
                            var l = j
                            while (l >= 0 && input[i][l].isDigit()) l--
                            l++

                            var num = 0
                            while (l < input[i].length && input[i][l].isDigit()) {
                                num = 10 * num + (input[i][l] - '0');
                                l++
                            }
                            j = l
                            ret += num
                            break
                        }
                    }
                }
                j++
            }
        }

        return ret
    }

    fun part2(input: List<String>): Int {
        var ret = 0

        for (i in 0 until input.size) {
            var j = 0
            while (j < input[i].length) {
                if (input[i][j] == '*') {
                    var nums = mutableListOf<Int>()
                    var seensNums = mutableListOf<Int>()
                    for (k in 0 until DX.size) {
                        val dx = j + DX[k]
                        val dy = i + DY[k]

                        if (dx < 0 || dx >= input[i].length || dy < 0 || dy >= input.size)
                            continue

                        if (input[dy][dx].isDigit()) {
                            var l = dx
                            while (l >= 0 && input[dy][l].isDigit()) l--
                            l++

                            val t = dy * input.size + l

                            if (seensNums.contains(t))
                                continue

                            seensNums.add(t)

                            var num = 0
                            while (l < input[dy].length && input[dy][l].isDigit()) {
                                num = 10 * num + (input[dy][l] - '0');
                                l++
                            }
                            nums.add(num)
                        }
                    }
                    if (nums.size == 2) {
                        ret += nums[0] * nums[1]
                    }
                }
                j++
            }
        }

        return ret
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part2(testInput) == 467835)

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
