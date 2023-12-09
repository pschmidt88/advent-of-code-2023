fun main() {

    fun readMaps(input: List<String>): List<Map<LongRange, LongRange>> {
        return input.drop(2).joinToString("\n").split("\n\n").map { section ->
            section.lines().drop(1).associate {
                it.split(" ").map { it.toLong() }.let { (dest, source, length) ->
                    (source until source + length) to (dest until dest + length)
                }
            }
        }
    }

    fun part1(input: List<String>): Long {
        val seeds = input.first().replace("seeds: ", "").split(" ").map { it.toLong() }
        val maps = readMaps(input)

        return seeds.minOf { seed ->
            maps.fold(seed) { aac, map ->
                map.entries.firstOrNull {
                    aac in it.key
                }?.let { (source, dest) ->
                    dest.first + (aac - source.first)
                } ?: aac
            }
        }
    }



    fun part2(input: List<String>): Long {
        val seeds = input.first().substringAfter(" ").split(" ").map { it.toLong() }.chunked(2)
            .map { it.first()..it.first() + it.last() }
        val maps = readMaps(input)

        return seeds.flatMap { seedRange ->
            maps.fold(listOf(seedRange)) { aac, rangeMap ->
                aac.flatMap { TODO("?!") }
            }
        }.minOf { it.first }
    }

    val testInput = readInput("Day05_test")
    check(part1(testInput) == 35.toLong())
    check(part2(testInput) == 46.toLong())

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
