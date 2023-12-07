fun main() {

    fun part1(input: List<String>): Int {
        val RANK = mapOf(
            '2' to 2,
            '3' to 3,
            '4' to 4,
            '5' to 5,
            '6' to 6,
            '7' to 7,
            '8' to 8,
            '9' to 9,
            'T' to 10,
            'J' to 11,
            'Q' to 12,
            'K' to 13,
            'A' to 14
        )

        data class Card(val card: String, val score: Int, val bid: Int) {


            fun compare(o: Card): Int {
                for (i in 0 until o.card.length) {
                    val a = RANK[card[i]]!!
                    val b = RANK[o.card[i]]!!

                    if (a != b) return a - b
                }
                return 0
            }
        }

        fun score(card: String): Int {
            var pairs = IntArray(6)
            val tmp = card.toCharArray().sorted()

            var i = 1
            var t = 1
            while (i < tmp.size) {
                if (tmp[i] == tmp[i - 1]) {
                    t++
                } else {
                    pairs[t]++
                    t = 1
                }
                i++
            }

            pairs[t]++
            var score = 0

            var fac = 10
            for (i in 1 .. 5) {
                score += pairs[i] * fac
                fac *= 10
            }

            return score
        }

        val cards = mutableListOf<Card>()
        for (line in input) {
            val splits = line.split(" ")

            cards.add(Card(splits[0], score(splits[0]), splits[1].toInt()))
        }

        cards.sortWith { a, b ->
            if (a.score < b.score) -1
            else if (a.score > b.score) +1
            else a.compare(b)
        }

        var ret = 0L
        for (i in 1 .. cards.size) {
            ret += (cards[i - 1].bid) * i
        }

        return ret.toInt()
    }

    fun part2(input: List<String>): Int {
        val RANK = mapOf(
            '2' to 2,
            '3' to 3,
            '4' to 4,
            '5' to 5,
            '6' to 6,
            '7' to 7,
            '8' to 8,
            '9' to 9,
            'T' to 10,
            'J' to 1,
            'Q' to 12,
            'K' to 13,
            'A' to 14
        )

        data class Card(val card: String, val score: Int, val bid: Int) {
            fun compare(o: Card): Int {
                for (i in 0 until o.card.length) {
                    val a = RANK[card[i]]!!
                    val b = RANK[o.card[i]]!!

                    if (a != b) return a - b
                }
                return 0
            }
        }

        fun score(card2: String): Int {
            val pairs = IntArray(6)

            var card = card2
            if (card == "JJJJJ") {
                card = "QQQQQ"
            }

            val maxC = card.groupBy { it }.filter { it.key != 'J' }.maxBy { it.value.size }
            val tmp = card.replace('J', maxC.key).toCharArray().sorted()

            var i = 1
            var t = 1
            while (i < tmp.size) {
                if (tmp[i] == tmp[i - 1]) {
                    t++
                } else {
                    pairs[t]++
                    t = 1
                }
                i++
            }

            pairs[t]++

            var score = 0

            var fac = 10
            for (i in 1 .. 5) {
                score += pairs[i] * fac
                fac *= 10
            }

            return score
        }

        val cards = mutableListOf<Card>()
        for (line in input) {
            val splits = line.split(" ")

            cards.add(Card(splits[0], score(splits[0]), splits[1].toInt()))
        }

        cards.sortWith { a, b ->
            if (a.score < b.score) -1
            else if (a.score > b.score) +1
            else a.compare(b)
        }

        var ret = 0L
        for (i in 1 .. cards.size) {
            ret += (cards[i - 1].bid) * i
        }

        return ret.toInt()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part2(testInput) == 5905)

    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}
