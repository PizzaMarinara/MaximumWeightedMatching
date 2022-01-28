package dev.efantini.maximumweightedmatching

import org.junit.Test

class MyTests {

    @Test
    fun testRound1() {
        val weightMatchList = MaximumWeightedMatching.maxWeightMatchingList(
            listOf(
                GraphEdge(8, 3, 181),
                GraphEdge(8, 4, 70),
                GraphEdge(8, 9, 70),
                GraphEdge(8, 6, 171),
                GraphEdge(8, 2, 40),
                GraphEdge(8, 5, 141),
                GraphEdge(8, 7, 131),
                GraphEdge(8, 1, 121),
                GraphEdge(3, 4, 173),
                GraphEdge(3, 9, 173),
                GraphEdge(3, 6, 173),
                GraphEdge(3, 2, 48),
                GraphEdge(3, 5, 48),
                GraphEdge(3, 7, 40),
                GraphEdge(3, 1, 133),
                GraphEdge(4, 9, 171),
                GraphEdge(4, 6, 70),
                GraphEdge(4, 2, 150),
                GraphEdge(4, 5, 150),
                GraphEdge(4, 7, 42),
                GraphEdge(4, 1, 136),
                GraphEdge(9, 6, 171),
                GraphEdge(9, 2, 150),
                GraphEdge(9, 5, 49),
                GraphEdge(9, 7, 143),
                GraphEdge(9, 1, 35),
                GraphEdge(6, 2, 150),
                GraphEdge(6, 5, 150),
                GraphEdge(6, 7, 143),
                GraphEdge(6, 1, 35),
                GraphEdge(2, 5, 141),
                GraphEdge(2, 7, 137),
                GraphEdge(2, 1, 133),
                GraphEdge(5, 7, 137),
                GraphEdge(5, 1, 133),
                GraphEdge(7, 1, 27)
            )
        )
        println(weightMatchList)
        assert(weightMatchList == listOf<Long>(-1, -1, 4, 8, 2, 7, 9, 5, 3, 6))
    }
}
