import kotlin.math.pow

fun main() {
    fun part1(input: List<String>): Int {
        return input
            .map { card -> parseCard(card) }
            .sumOf { it.worthiness() }
    }

    fun part2(input: List<String>): Int {
        val cards = input.map { line ->
            parseCard(line)
        }

        val tracker = IntArray(cards.size) { 1 }
        cards.forEachIndexed { index, card ->
            repeat(card.matchingNumbersCount) {
                tracker[index+it+1] += tracker[index]
            }
        }

        return tracker.sum()
    }


// test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 30)

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}

fun parseCard(line: String): ScratchCard {
    val (winning, scratchnumbers) = line
        .substringAfter(":")
        .split("|")
        .map {
            it.trim().replace("  ", " ")
                .split(" ")
                .map { number -> number.toInt() }
        }

    return ScratchCard(winning, scratchnumbers)
}

data class ScratchCard(
    private val winningNumbers: List<Int>,
    private val numbers: List<Int>
) {
    val matchingNumbersCount: Int = numbers.count { winningNumbers.contains(it) }

    fun worthiness(): Int {
        return 2.0.pow(matchingNumbersCount - 1).toInt()
    }
}
