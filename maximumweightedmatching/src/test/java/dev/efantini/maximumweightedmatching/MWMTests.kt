package dev.efantini.maximumweightedmatching

import org.junit.Test

class MWMTests {

    @Test
    fun test10() {
        val weightMatchList = MaximumWeightedMatching.maxWeightMatchingList(
            listOf()
        )
        println(weightMatchList)
        assert(weightMatchList == listOf<Long>())
    }

    @Test
    fun test11() {
        val weightMatchList = MaximumWeightedMatching.maxWeightMatchingList(
            listOf(
                GraphEdge(0, 1, 1)
            )
        )
        println(weightMatchList)
        assert(weightMatchList == listOf<Long>(1, 0))
    }

    @Test
    fun test12() {
        val weightMatchList = MaximumWeightedMatching.maxWeightMatchingList(
            listOf(
                GraphEdge(1, 2, 10),
                GraphEdge(2, 3, 11)
            )
        )
        println(weightMatchList)
        assert(weightMatchList == listOf<Long>(-1, -1, 3, 2))
    }

    @Test
    fun test13() {
        val weightMatchList = MaximumWeightedMatching.maxWeightMatchingList(
            listOf(
                GraphEdge(1, 2, 5),
                GraphEdge(2, 3, 11),
                GraphEdge(3, 4, 5)
            )
        )
        println(weightMatchList)
        assert(weightMatchList == listOf<Long>(-1, -1, 3, 2, -1))
    }

    @Test
    fun test14_maxcard() {
        val weightMatchList = MaximumWeightedMatching.maxWeightMatchingList(
            listOf(
                GraphEdge(1, 2, 5),
                GraphEdge(2, 3, 11),
                GraphEdge(3, 4, 5)
            ),
            maxcardinality = true
        )
        println(weightMatchList)
        assert(weightMatchList == listOf<Long>(-1, 2, 1, 4, 3))
    }

    @Test
    fun test16_negative() {
        val weightMatchList = MaximumWeightedMatching.maxWeightMatchingList(
            listOf(
                GraphEdge(1, 2, 2),
                GraphEdge(1, 3, -2),
                GraphEdge(2, 3, 1),
                GraphEdge(2, 4, -1),
                GraphEdge(3, 4, -6)
            ),
            maxcardinality = false
        )
        println(weightMatchList)
        assert(weightMatchList == listOf<Long>(-1, 2, 1, -1, -1))
    }
    @Test
    fun test16_negative_maxcard() {
        val weightMatchList = MaximumWeightedMatching.maxWeightMatchingList(
            listOf(
                GraphEdge(1, 2, 2),
                GraphEdge(1, 3, -2),
                GraphEdge(2, 3, 1),
                GraphEdge(2, 4, -1),
                GraphEdge(3, 4, -6)
            ),
            maxcardinality = true
        )
        println(weightMatchList)
        assert(weightMatchList == listOf<Long>(-1, 3, 4, 1, 2))
    }

    @Test
    fun test20_sblossom_1() {
        val weightMatchList = MaximumWeightedMatching.maxWeightMatchingList(
            listOf(
                GraphEdge(1, 2, 8),
                GraphEdge(1, 3, 9),
                GraphEdge(2, 3, 10),
                GraphEdge(3, 4, 7)
            )
        )
        println(weightMatchList)
        assert(weightMatchList == listOf<Long>(-1, 2, 1, 4, 3))
    }

    @Test
    fun test20_sblossom_2() {
        val weightMatchList = MaximumWeightedMatching.maxWeightMatchingList(
            listOf(
                GraphEdge(1, 2, 8),
                GraphEdge(1, 3, 9),
                GraphEdge(2, 3, 10),
                GraphEdge(3, 4, 7),
                GraphEdge(1, 6, 5),
                GraphEdge(4, 5, 6)
            )
        )
        println(weightMatchList)
        assert(weightMatchList == listOf<Long>(-1, 6, 3, 2, 5, 4, 1))
    }

    @Test
    fun test21_tblossom_1() {
        val weightMatchList = MaximumWeightedMatching.maxWeightMatchingList(
            listOf(
                GraphEdge(1, 2, 9),
                GraphEdge(1, 3, 8),
                GraphEdge(2, 3, 10),
                GraphEdge(1, 4, 5),
                GraphEdge(4, 5, 4),
                GraphEdge(1, 6, 3)
            )
        )
        println(weightMatchList)
        assert(weightMatchList == listOf<Long>(-1, 6, 3, 2, 5, 4, 1))
    }

    @Test
    fun test21_tblossom_2() {
        val weightMatchList = MaximumWeightedMatching.maxWeightMatchingList(
            listOf(
                GraphEdge(1, 2, 9),
                GraphEdge(1, 3, 8),
                GraphEdge(2, 3, 10),
                GraphEdge(1, 4, 5),
                GraphEdge(4, 5, 4),
                GraphEdge(1, 6, 4)
            )
        )
        println(weightMatchList)
        assert(weightMatchList == listOf<Long>(-1, 6, 3, 2, 5, 4, 1))
    }

    @Test
    fun test21_tblossom_3() {
        val weightMatchList = MaximumWeightedMatching.maxWeightMatchingList(
            listOf(
                GraphEdge(1, 2, 9),
                GraphEdge(1, 3, 8),
                GraphEdge(2, 3, 10),
                GraphEdge(1, 4, 5),
                GraphEdge(4, 5, 4),
                GraphEdge(3, 6, 4)
            )
        )
        println(weightMatchList)
        assert(weightMatchList == listOf<Long>(-1, 2, 1, 6, 5, 4, 3))
    }

    @Test
    fun test22_s_nest() {
        val weightMatchList = MaximumWeightedMatching.maxWeightMatchingList(
            listOf(
                GraphEdge(1, 2, 9),
                GraphEdge(1, 3, 9),
                GraphEdge(2, 3, 10),
                GraphEdge(2, 4, 8),
                GraphEdge(3, 5, 8),
                GraphEdge(4, 5, 10),
                GraphEdge(5, 6, 6)
            )
        )
        println(weightMatchList)
        assert(weightMatchList == listOf<Long>(-1, 3, 4, 1, 2, 6, 5))
    }

    @Test
    fun test23_s_relabel_nest() {
        val weightMatchList = MaximumWeightedMatching.maxWeightMatchingList(
            listOf(
                GraphEdge(1, 2, 10),
                GraphEdge(1, 7, 10),
                GraphEdge(2, 3, 12),
                GraphEdge(3, 4, 20),
                GraphEdge(3, 5, 20),
                GraphEdge(4, 5, 25),
                GraphEdge(5, 6, 10),
                GraphEdge(6, 7, 10),
                GraphEdge(7, 8, 8)
            )
        )
        println(weightMatchList)
        assert(weightMatchList == listOf<Long>(-1, 2, 1, 4, 3, 6, 5, 8, 7))
    }

    @Test
    fun test24_s_nest_expand() {
        val weightMatchList = MaximumWeightedMatching.maxWeightMatchingList(
            listOf(
                GraphEdge(1, 2, 8),
                GraphEdge(1, 3, 8),
                GraphEdge(2, 3, 10),
                GraphEdge(2, 4, 12),
                GraphEdge(3, 5, 12),
                GraphEdge(4, 5, 14),
                GraphEdge(4, 6, 12),
                GraphEdge(5, 7, 12),
                GraphEdge(6, 7, 14),
                GraphEdge(7, 8, 12)
            )
        )
        println(weightMatchList)
        assert(weightMatchList == listOf<Long>(-1, 2, 1, 5, 6, 3, 4, 8, 7))
    }

    @Test
    fun test25_s_t_expand() {
        val weightMatchList = MaximumWeightedMatching.maxWeightMatchingList(
            listOf(
                GraphEdge(1, 2, 23),
                GraphEdge(1, 5, 22),
                GraphEdge(1, 6, 15),
                GraphEdge(2, 3, 25),
                GraphEdge(3, 4, 22),
                GraphEdge(4, 5, 25),
                GraphEdge(4, 8, 14),
                GraphEdge(5, 7, 13)
            )
        )
        println(weightMatchList)
        assert(weightMatchList == listOf<Long>(-1, 6, 3, 2, 8, 7, 1, 5, 4))
    }

    @Test
    fun test26_s_nest_t_expand() {
        val weightMatchList = MaximumWeightedMatching.maxWeightMatchingList(
            listOf(
                GraphEdge(1, 2, 19),
                GraphEdge(1, 3, 20),
                GraphEdge(1, 8, 8),
                GraphEdge(2, 3, 25),
                GraphEdge(2, 4, 18),
                GraphEdge(3, 5, 18),
                GraphEdge(4, 5, 13),
                GraphEdge(4, 7, 7),
                GraphEdge(5, 6, 7)
            )
        )
        println(weightMatchList)
        assert(weightMatchList == listOf<Long>(-1, 8, 3, 2, 7, 6, 5, 4, 1))
    }

    @Test
    fun test30_tnasty_expand() {
        val weightMatchList = MaximumWeightedMatching.maxWeightMatchingList(
            listOf(
                GraphEdge(1, 2, 45),
                GraphEdge(1, 5, 45),
                GraphEdge(2, 3, 50),
                GraphEdge(3, 4, 45),
                GraphEdge(4, 5, 50),
                GraphEdge(1, 6, 30),
                GraphEdge(3, 9, 35),
                GraphEdge(4, 8, 35),
                GraphEdge(5, 7, 26),
                GraphEdge(9, 10, 5)
            )
        )
        println(weightMatchList)
        assert(weightMatchList == listOf<Long>(-1, 6, 3, 2, 8, 7, 1, 5, 4, 10, 9))
    }

    @Test
    fun test31_tnasty2_expand() {
        val weightMatchList = MaximumWeightedMatching.maxWeightMatchingList(
            listOf(
                GraphEdge(1, 2, 45),
                GraphEdge(1, 5, 45),
                GraphEdge(2, 3, 50),
                GraphEdge(3, 4, 45),
                GraphEdge(4, 5, 50),
                GraphEdge(1, 6, 30),
                GraphEdge(3, 9, 35),
                GraphEdge(4, 8, 26),
                GraphEdge(5, 7, 40),
                GraphEdge(9, 10, 5)
            )
        )
        println(weightMatchList)
        assert(weightMatchList == listOf<Long>(-1, 6, 3, 2, 8, 7, 1, 5, 4, 10, 9))
    }

    @Test
    fun test32_t_expand_leastslack() {
        val weightMatchList = MaximumWeightedMatching.maxWeightMatchingList(
            listOf(
                GraphEdge(1, 2, 45),
                GraphEdge(1, 5, 45),
                GraphEdge(2, 3, 50),
                GraphEdge(3, 4, 45),
                GraphEdge(4, 5, 50),
                GraphEdge(1, 6, 30),
                GraphEdge(3, 9, 35),
                GraphEdge(4, 8, 28),
                GraphEdge(5, 7, 26),
                GraphEdge(9, 10, 5)
            )
        )
        println(weightMatchList)
        assert(weightMatchList == listOf<Long>(-1, 6, 3, 2, 8, 7, 1, 5, 4, 10, 9))
    }

    @Test
    fun test33_nest_tnasty_expand() {
        val weightMatchList = MaximumWeightedMatching.maxWeightMatchingList(
            listOf(
                GraphEdge(1, 2, 45),
                GraphEdge(1, 7, 45),
                GraphEdge(2, 3, 50),
                GraphEdge(3, 4, 45),
                GraphEdge(4, 5, 95),
                GraphEdge(4, 6, 94),
                GraphEdge(5, 6, 94),
                GraphEdge(6, 7, 50),
                GraphEdge(1, 8, 30),
                GraphEdge(3, 11, 35),
                GraphEdge(5, 9, 36),
                GraphEdge(7, 10, 26),
                GraphEdge(11, 12, 5)
            )
        )
        println(weightMatchList)
        assert(weightMatchList == listOf<Long>(-1, 8, 3, 2, 6, 9, 4, 10, 1, 5, 7, 12, 11))
    }

    @Test
    fun test34_nest_relabel_expand() {
        val weightMatchList = MaximumWeightedMatching.maxWeightMatchingList(
            listOf(
                GraphEdge(1, 2, 40),
                GraphEdge(1, 3, 40),
                GraphEdge(2, 3, 60),
                GraphEdge(2, 4, 55),
                GraphEdge(3, 5, 55),
                GraphEdge(4, 5, 50),
                GraphEdge(1, 8, 15),
                GraphEdge(5, 7, 30),
                GraphEdge(7, 6, 10),
                GraphEdge(8, 10, 10),
                GraphEdge(4, 9, 30)
            )
        )
        println(weightMatchList)
        assert(weightMatchList == listOf<Long>(-1, 2, 1, 5, 9, 3, 7, 6, 10, 4, 8))
    }
}
