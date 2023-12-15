
fun main() {

    fun hash(s: String): Int {
        var ret = 0
        for (c in s) {
            ret += c.code
            ret *= 17
            ret %= 256
        }

        return ret
    }

    fun part1(input: List<String>): Long {
        var ret = 0L

        for (line in input) {
            val splits = line.split(",")
            for (s in splits) {
                ret += hash(s)
            }
        }

        return ret
    }

    data class Item(val label: String, var focal: Int, val pos: Int) {}

    class Box {
        var nextPos = 1

        var items = mutableMapOf<String, Item>()

        private fun eqOp(label: String, focal: Int) {
            val item = items.get(label)
            if (item != null) {
                item.focal = focal
            } else {
                items[label] = Item(label, focal, nextPos)
                nextPos++
            }
        }

        private fun dashOp(label: String) {
            items.remove(label)
        }

        fun op(s: String) {
            if (s.contains("=")) {
                val splits = s.split("=")
                eqOp(splits[0], splits[1].toInt())
            } else {
                dashOp(s.substring(0, s.indexOf("-")))
            }
        }
    }

    fun hashItem(s: String): Int {
        if (s.contains("=")) {
            val splits = s.split("=")
            return hash(splits[0])
        } else {
            return hash(s.substring(0, s.indexOf("-")))
        }
    }

    fun part2(input: List<String>): Long {
        val boxes = Array(256) { Box() }
        for (line in input) {
            val splits = line.split(",")
            for (item in splits) {
                val box = hashItem(item)
                boxes[box].op(item)
            }
        }

        var ret = 0L

        for (box in boxes.indices) {
            val items = boxes[box].items.entries.sortedBy { it.value.pos }

            for (k in items.indices) {
                ret += (box + 1) * (k + 1) * items[k].value.focal
            }
        }

        return ret
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day15_test")
    check(part1(testInput) == 1320L)
    check(part2(testInput) == 145L)

    val input = readInput("Day15")
    part1(input).println()
    part2(input).println()
}
