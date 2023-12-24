fun main() {
    fun part1(input: List<String>): Int {
        return input.map { it.split(" ").let { (value, bid) -> Hand(value, bid.toInt()) } }
            .sorted()
            .mapIndexed { index, hand ->
                hand.bid * (index + 1)
            }
            .sum()
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    check(part1(readInput("Day07_test")) == 6440)
//    check(part2(readInput("Day07_test")) == 71503)

    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}

class Hand(
    val value: String,
    val bid: Int
) : Comparable<Hand> {

    enum class Type(val value: Int) {
        FIVE_OF_A_KIND(7),
        FOUR_OF_A_KIND(6),
        FULL_HOUSE(5),
        THREE_OF_A_KIND(4),
        TWO_PAIRS(3),
        PAIR(2),
        HIGH_CARD(1)
    }

    private val countValues = value.groupBy { it }.mapValues { it.value.count() }
    private val pairValues = countValues.filterValues { it == 2 }.keys.toList()

    private val isFiveOfAKind: Boolean = countValues.containsValue(5)
    private val isFourOfAKind: Boolean = countValues.containsValue(4)
    private val isThreeOfAKind: Boolean = countValues.containsValue(3)
    private val hasPair = pairValues.count() == 1
    private val isFullHouse: Boolean = isThreeOfAKind && hasPair
    private val hasTwoPairs = pairValues.count() == 2

    private fun getType(): Type {
        return when {
            isFiveOfAKind -> Type.FIVE_OF_A_KIND
            isFourOfAKind -> Type.FOUR_OF_A_KIND
            isThreeOfAKind -> Type.THREE_OF_A_KIND
            isFullHouse -> Type.FULL_HOUSE
            hasTwoPairs -> Type.TWO_PAIRS
            hasPair -> Type.PAIR
            else -> Type.HIGH_CARD
        }
    }

    override fun compareTo(other: Hand): Int {
        if (this.getType() == other.getType()) {
            return this.handSpecificValue()
        }

        return this.getType().value - other.getType().value
    }

    private val factors = listOf(
        371293, 28561, 2197, 169, 13
    )

    private fun handSpecificValue(): Int {
        return when (this.getType()) {
            Type.FIVE_OF_A_KIND -> this.countValues.filterValues { it == 5 }.keys.first().getValue()
            Type.FOUR_OF_A_KIND ->
                this.countValues.filterValues { it == 4 }.keys.first().getValue() * factors[0] +
                        this.countValues.filterValues { it == 1 }.keys.first().getValue() * factors[1]
            Type.FULL_HOUSE -> this.countValues.filterValues { it == 3 }.keys.first().getValue()*factors[0]+
                    this.countValues.filterValues { it == 2 }.keys.first().getValue()*factors[1]
            Type.THREE_OF_A_KIND ->
                this.countValues.filterValues { it == 3}.keys.first().getValue() * factors[0]
            Type.TWO_PAIRS -> TODO()
            Type.PAIR -> TODO()
            Type.HIGH_CARD -> TODO()
        }
    }
}

fun Char.getValue(): Int {
    if (this.isDigit()) {
        return this.digitToInt()
    }
    return when (this) {
        'T' -> 10
        'J' -> 11
        'Q' -> 12
        'K' -> 13
        'A' -> 14
        else -> throw Exception("Invalid char, cannot get value for $this")
    }
}
