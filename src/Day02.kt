fun main() {
    fun part1(input: List<String>): Int {
        return input.map { it.readGame() }.filter { it.isPossible() }.sumOf { it.id }
    }

    fun part2(input: List<String>): Int {
        return input.map { it.readGame() }
            .map { it.maxSeenRed*it.maxSeenBlue*it.maxSeenGreen }
            .sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part2(testInput) == 2286)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}

private fun String.readGame(): Game {
    val id = "Game (\\d+)".toRegex().find(this).let {
        it?.groups?.get(1)?.value?.toInt()
    } ?: throw Exception("no id found")

    val result = "((?<blue>\\d+) blue)|((?<red>\\d+) red)|((?<green>\\d+) green)".toRegex().findAll(this)

    val maxSeenBlue = result.mapNotNull { it.groups["blue"]?.value }
        .map { it.toInt() }
        .max()

    val maxSeenRed = result.mapNotNull { it.groups["red"]?.value }
        .map { it.toInt() }
        .max()

    val maxSeenGreen = result.mapNotNull { it.groups["green"]?.value }
        .map { it.toInt() }
        .max()

    return Game(id, maxSeenRed, maxSeenBlue, maxSeenGreen)
}

const val allowedRed = 12
const val allowedGreen = 13
const val allowedBlue = 14
data class Game(
    val id: Int,
    val maxSeenRed: Int = 0,
    val maxSeenBlue: Int = 0,
    val maxSeenGreen: Int = 0
) {
    fun isPossible(): Boolean {
        return maxSeenRed <= allowedRed &&
                maxSeenBlue <= allowedBlue &&
                maxSeenGreen <= allowedGreen
    }
}
