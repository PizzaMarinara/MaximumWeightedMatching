package dev.efantini.maximumweightedmatching

import kotlin.math.max

object MaximumWeightedMatching {

    val debugMode = false

    fun maxWeightMatching(
        edges: List<GraphEdge>,
        maxcardinality: Boolean = false
    ): List<Pair<Long, Long>> {
        val returnPairs = mutableListOf<Pair<Long, Long>>()

        val mate = maxWeightMatchingList(edges, maxcardinality)

        mate.forEachIndexed { index, l ->
            if (returnPairs.none {
                (it.first == l || it.second == l) && l.toInt() != -1
            }
            ) {
                returnPairs.add(Pair(index.toLong(), l))
            }
        }
        return returnPairs
    }

    fun maxWeightMatchingList(
        edges: List<GraphEdge>,
        maxcardinality: Boolean = false
    ): MutableList<Long> {

        if (edges.isEmpty())
            return mutableListOf()

        val nedges = edges.count()
        var nvertex = 0
        edges.forEach { graphEdge ->
            if (graphEdge.node1 >= 0 &&
                graphEdge.node2 >= 0 &&
                graphEdge.node1 != graphEdge.node2
            ) {
                if (graphEdge.node1 >= nvertex)
                    nvertex = (graphEdge.node1 + 1).toInt()
                if (graphEdge.node2 >= nvertex)
                    nvertex = (graphEdge.node2 + 1).toInt()
            }
        }
        val maxweight = max(0, edges.maxOf { it.weight })

        val endpoint = mutableListOf<Long>()
        edges.forEach {
            endpoint.add(it.node1)
            endpoint.add(it.node2)
        }

        val neighbend = mutableListOf<MutableList<Int>>()
        edges.forEachIndexed { k, graphEdge ->
            if (neighbend.size <= graphEdge.node1.toInt()) {
                for (help in neighbend.size until graphEdge.node1.toInt()) {
                    neighbend.add(help, mutableListOf())
                }
                neighbend.add(graphEdge.node1.toInt(), mutableListOf())
            }
            neighbend[graphEdge.node1.toInt()].add((2 * k) + 1)
            if (neighbend.size <= graphEdge.node2.toInt()) {
                for (help in neighbend.size until graphEdge.node2.toInt()) {
                    neighbend.add(help, mutableListOf())
                }
                neighbend.add(graphEdge.node2.toInt(), mutableListOf())
            }
            neighbend[graphEdge.node2.toInt()].add((2 * k))
        }

        val mate = mutableListOf<Long>()
        for (i in 0 until nvertex) {
            mate.add(-1)
        }

        val label = mutableListOf<Int>()
        for (i in 0 until nvertex) {
            label.add(0)
        }
        for (i in 0 until nvertex) {
            label.add(0)
        }

        val labelend = mutableListOf<Int>()
        for (i in 0 until nvertex) {
            labelend.add(-1)
        }
        for (i in 0 until nvertex) {
            labelend.add(-1)
        }

        val inblossom = mutableListOf<Int>()
        for (i in 0 until nvertex) {
            inblossom.add(i)
        }

        val blossomparent = mutableListOf<Int>()
        for (i in 0 until nvertex) {
            blossomparent.add(-1)
        }
        for (i in 0 until nvertex) {
            blossomparent.add(-1)
        }

        val blossomchilds = mutableListOf<MutableList<Int>>()
        for (i in 0 until nvertex) {
            blossomchilds.add(mutableListOf())
        }
        for (i in 0 until nvertex) {
            blossomchilds.add(mutableListOf())
        }

        val blossombase = mutableListOf<Int>()
        for (i in 0 until nvertex) {
            blossombase.add(i)
        }
        for (i in 0 until nvertex) {
            blossombase.add(-1)
        }

        val blossomendps = mutableListOf<MutableList<Int>>()
        for (i in 0 until nvertex) {
            blossomendps.add(mutableListOf())
        }
        for (i in 0 until nvertex) {
            blossomendps.add(mutableListOf())
        }

        val bestedge = mutableListOf<Int>()
        for (i in 0 until nvertex) {
            bestedge.add(-1)
        }
        for (i in 0 until nvertex) {
            bestedge.add(-1)
        }

        val blossombestedges = mutableListOf<MutableList<Int>>()
        for (i in 0 until nvertex) {
            blossombestedges.add(mutableListOf())
        }
        for (i in 0 until nvertex) {
            blossombestedges.add(mutableListOf())
        }

        val unusedblossoms = mutableListOf<Int>()
        for (i in nvertex until nvertex * 2) {
            unusedblossoms.add(i)
        }

        val dualvar = mutableListOf<Long>()
        for (i in 0 until nvertex) {
            dualvar.add(maxweight)
        }
        for (i in 0 until nvertex) {
            dualvar.add(0)
        }

        val allowedge = mutableListOf<Boolean>()
        for (i in 0 until nedges) {
            allowedge.add(false)
        }

        val queue = mutableListOf<Int>()

        fun slack(k: Int): Long {
            return (
                dualvar[edges[k].node1.toInt()] +
                    dualvar[edges[k].node2.toInt()] -
                    (2 * edges[k].weight)
                )
        }

        fun blossomLeaves(b: Int): MutableList<Int> {
            return if (b < nvertex) {
                mutableListOf(b)
            } else {
                blossomchilds[b].flatMap { t ->
                    if (t < nvertex) {
                        mutableListOf(t)
                    } else {
                        blossomLeaves(t)
                    }
                }.toMutableList()
            }
        }

        fun assignLabel(w: Int, t: Int, p: Int) {
            if (debugMode) println("assignLabel($w,$t,$p)")
            val b = inblossom[w]
            assert(label[w] == 0 && label[b] == 0)
            label[w] = t
            label[b] = t
            labelend[w] = p
            labelend[b] = p
            bestedge[w] = -1
            bestedge[b] = -1
            if (t == 1) {
                queue.addAll(blossomLeaves(b))
                if (debugMode) println("PUSH ${blossomLeaves(b)}")
            } else if (t == 2) {
                val base = blossombase[b]
                assert(mate[base] >= 0)
                assignLabel(
                    endpoint[mate[base].toInt()].toInt(),
                    1,
                    mate[base].xor(1).toInt()
                )
            }
        }

        fun scanBlossom(parV: Int, parW: Int): Int {
            if (debugMode) println("scanBlossom($parV,$parW)")
            var v = parV
            var w = parW
            val path = mutableListOf<Int>()
            var base = -1
            while (v != -1 || w != -1) {
                var b = inblossom[v]
                if (label[b].and(4) != 0) {
                    base = blossombase[b]
                    break
                }
                assert(label[b] == 1)
                path.add(b)
                label[b] = 5
                assert(labelend[b] == mate[blossombase[b]].toInt())
                if (labelend[b] == -1) {
                    v = -1
                } else {
                    v = endpoint[labelend[b]].toInt()
                    b = inblossom[v]
                    assert(label[b] == 2)
                    assert(labelend[b] >= 0)
                    v = endpoint[labelend[b]].toInt()
                }
                if (w != -1) {
                    v = w.also { w = v }
                }
            }
            path.forEach {
                label[it] = 1
            }
            return base
        }

        fun addBlossom(base: Int, k: Int) {
            val edge = edges[k]
            var v = edge.node1.toInt()
            var w = edge.node2.toInt()
            val bb = inblossom[base]
            var bv = inblossom[v]
            var bw = inblossom[w]
            val b = unusedblossoms.removeLast()
            if (debugMode) println("addBlossom($base,$k) (v=$v w=$w) -> $b")
            blossombase[b] = base
            blossomparent[b] = -1
            blossomparent[bb] = b
            val path = mutableListOf<Int>()
            blossomchilds[b] = path
            val endps = mutableListOf<Int>()
            blossomendps[b] = endps
            while (bv != bb) {
                blossomparent[bv] = b
                path.add(bv)
                endps.add(labelend[bv])
                assert(
                    label[bv] == 2 ||
                        (label[bv] == 1 && labelend[bv] == mate[blossombase[bv]].toInt())
                )
                assert(labelend[bv] >= 0)
                v = endpoint[labelend[bv]].toInt()
                bv = inblossom[v]
            }
            path.add(bv)
            path.reverse()
            endps.reverse()
            endps.add(2 * k)
            while (bw != bb) {
                blossomparent[bw] = b
                path.add(bw)
                endps.add(labelend[bw].xor(1))
                assert(
                    label[bw] == 2 ||
                        (label[bw] == 1 && labelend[bw] == mate[blossombase[bw]].toInt())
                )
                assert(labelend[bw] >= 0)
                w = endpoint[labelend[bw]].toInt()
                bw = inblossom[w]
            }
            assert(label[bb] == 1)
            label[b] = 1
            labelend[b] = labelend[bb]
            dualvar[b] = 0
            blossomLeaves(b).forEach { vIns ->
                if (label[inblossom[vIns]] == 2) {
                    queue.add(vIns)
                }
                inblossom[vIns] = b
            }
            val bestedgeto = mutableListOf<Int>()
            for (i in 0 until nvertex) {
                bestedgeto.add(-1)
            }
            for (i in 0 until nvertex) {
                bestedgeto.add(-1)
            }
            path.forEach {
                val nblists: MutableList<MutableList<Int>> = if (blossombestedges[it].isEmpty()) {
                    mutableListOf(blossombestedges[it])
                } else {
                    blossomLeaves(it).map { blV ->
                        neighbend[blV].map { neighBlV ->
                            neighBlV.floorDiv(2)
                        }.toMutableList()
                    }.toMutableList()
                }
                nblists.forEach { nbList ->
                    nbList.forEach { intNbList ->
                        val edgeNb = edges[intNbList]
                        var i = edgeNb.node1.toInt()
                        var j = edgeNb.node2.toInt()
                        if (inblossom[j] == b) {
                            i = j.also { j = i }
                        }
                        val bj = inblossom[j]
                        if (bj != b && label[bj] == 1 &&
                            (bestedgeto[bj] == -1 || slack(intNbList) < slack(bestedgeto[bj]))
                        ) {
                            bestedgeto[bj] = k
                        }
                    }
                }
                blossombestedges[it] = mutableListOf()
                bestedge[it] = -1
            }
            blossombestedges[b] = bestedgeto.filter { it != -1 }.toMutableList()
            bestedge[b] = -1
            blossombestedges[b].forEach {
                if (bestedge[b] == -1 || slack(it) < slack(bestedge[b])) {
                    bestedge[b] = it
                }
            }
            if (debugMode) println("blossomchilds[$b]=${blossomchilds[b]}")
        }

        // Some sketchy
        fun expandBlossom(b: Int, endstage: Boolean) {
            if (debugMode) println("expandBlossom($b,$endstage) ${blossomchilds[b]}")
            blossomchilds[b].forEach { s ->
                blossomparent[s] = -1
                if (s < nvertex) {
                    inblossom[s] = s
                } else if (endstage && dualvar[s].toInt() == 0) {
                    expandBlossom(s, endstage)
                } else {
                    blossomLeaves(s).forEach { v ->
                        inblossom[v] = s
                    }
                }
            }
            if (!endstage && label[b] == 2) {
                assert(labelend[b] >= 0)
                val entrychild = inblossom[endpoint[labelend[b].xor(1)].toInt()]
                var j = blossomchilds[b].indexOf(entrychild)
                val jstep: Int
                val endptrick: Int
                if (j.and(1) != 0) {
                    j -= blossomchilds[b].size
                    jstep = 1
                    endptrick = 0
                } else {
                    jstep = -1
                    endptrick = 0
                }
                var p = labelend[b]
                while (j != 0) {
                    if (p.xor(1) < 0) {
                        label[endpoint[endpoint.lastIndex + (p.xor(1)) + 1].toInt()] = 0
                    } else {
                        label[endpoint[p.xor(1)].toInt()] = 0
                    }
                    var innerLabelIndex = j - endptrick
                    if (innerLabelIndex < 0) {
                        innerLabelIndex = blossomendps[b].lastIndex + (j - endptrick) + 1
                    }
                    var outerLabelIndex = blossomendps[b][innerLabelIndex].xor(endptrick).xor(1)
                    if (outerLabelIndex < 0) {
                        outerLabelIndex = endpoint.lastIndex + (
                            blossomendps[b][innerLabelIndex].xor(endptrick).xor(1)
                            ) + 1
                    }
                    label[endpoint[outerLabelIndex].toInt()] = 0
                    if (p.xor(1) < 0) {
                        assignLabel(endpoint[endpoint.lastIndex + (p.xor(1)) + 1].toInt(), 2, p)
                    } else {
                        assignLabel(endpoint[p.xor(1)].toInt(), 2, p)
                    }
                    allowedge[blossomendps[b][innerLabelIndex].floorDiv(2)] = true
                    j += jstep
                    innerLabelIndex = j - endptrick
                    if (innerLabelIndex < 0) {
                        innerLabelIndex = blossomendps[b].lastIndex + (j - endptrick) + 1
                    }
                    p = blossomendps[b][innerLabelIndex].xor(endptrick)
                    allowedge[p.floorDiv(2)] = true
                    j += jstep
                }
                var bv = blossomchilds[b][j]
                label[bv] = 2
                label[endpoint[p.xor(1)].toInt()] = label[bv]
                labelend[endpoint[p.xor(1)].toInt()] = p
                labelend[bv] = p
                bestedge[bv] = -1
                j += jstep
                while (
                    if (j < 0) {
                        blossomchilds[b][blossomchilds[b].lastIndex + j + 1]
                    } else {
                        blossomchilds[b][j]
                    }
                    != entrychild
                ) {
                    bv = if (j < 0) {
                        blossomchilds[b][blossomchilds[b].lastIndex + j + 1]
                    } else {
                        blossomchilds[b][j]
                    }
                    if (label[bv] == 1) {
                        j += jstep
                        continue
                    }
                    // Sketchy part
                    /*
                    for (v in blossomLeaves(bv)) {
                        if(label[v] != 0)
                            break
                    }
                    if(label[v] != 0) { // Gives error?
                        assert
                    }
                    */
                    for (v in blossomLeaves(bv)) {
                        if (label[v] != 0) { // Gives error?
                            assert(label[v] == 2)
                            assert(inblossom[v] == bv)
                            label[v] = 0
                            label[endpoint[mate[blossombase[bv]].toInt()].toInt()] = 0
                            assignLabel(v, 2, labelend[v])
                        }
                    }
                    j += jstep
                }
            }
            labelend[b] = -1
            label[b] = labelend[b]
            blossomendps[b] = mutableListOf()
            blossomchilds[b] = blossomendps[b]
            blossombase[b] = -1
            blossombestedges[b] = mutableListOf()
            bestedge[b] = -1
            unusedblossoms.add(b)
        }

        fun augmentBlossom(b: Int, v: Int) {
            if (debugMode) println("augmentBlossom($b,$v)")
            var t = v
            val jstep: Int
            val endptrick: Int
            while (blossomparent[t] != b) {
                t = blossomparent[t]
            }
            if (t >= nvertex) {
                augmentBlossom(t, v)
            }
            val i = blossomchilds[b].indexOf(t)
            var j = blossomchilds[b].indexOf(t)
            if (i.and(1) != 0) {
                j -= blossomchilds[b].size
                jstep = 1
                endptrick = 0
            } else {
                jstep = -1
                endptrick = 1
            }
            while (j != 0) {
                j += jstep
                t = if (j < 0) {
                    blossomchilds[b][blossomchilds[b].lastIndex + j + 1]
                } else {
                    blossomchilds[b][j]
                }
                val p = if (j - endptrick < 0) {
                    blossomendps[b][blossomchilds[b].lastIndex + (j - endptrick) + 1]
                        .xor(endptrick)
                } else {
                    blossomendps[b][j - endptrick].xor(endptrick)
                }
                if (t >= nvertex) {
                    augmentBlossom(t, endpoint[p].toInt())
                }
                j += jstep
                t = if (j < 0) {
                    blossomchilds[b][blossomchilds[b].lastIndex + j + 1]
                } else {
                    blossomchilds[b][j]
                }
                if (t >= nvertex) {
                    augmentBlossom(t, endpoint[p.xor(1)].toInt())
                }
                if (p < 0) {
                    mate[endpoint[endpoint.lastIndex + p + 1].toInt()] = p.xor(1).toLong()
                } else {
                    mate[endpoint[p].toInt()] = p.xor(1).toLong()
                }
                if (p.xor(1) < 0) {
                    mate[endpoint[endpoint.lastIndex + (p.xor(1)) + 1].toInt()] = p.toLong()
                } else {
                    mate[endpoint[p.xor(1)].toInt()] = p.toLong()
                }
                if (debugMode) println(
                    "PAIR " +
                        "${if (p < 0) endpoint[endpoint.lastIndex + p + 1] else endpoint[p]}" +
                        " " +
                        "${if (p.xor(1) < 0)
                            endpoint[endpoint.lastIndex + (p.xor(1)) + 1]
                        else
                            endpoint[p.xor(1)]}" +
                        " (k=${(p / 2)})"
                )
            }
            blossomchilds[b] =
                (
                    blossomchilds[b].slice(i until blossomchilds[b].size) +
                        blossomchilds[b].slice(0 until i).toMutableList()
                    ).toMutableList()
            blossomendps[b] =
                (
                    blossomendps[b].slice(i until blossomendps[b].size) +
                        blossomendps[b].slice(0 until i).toMutableList()
                    ).toMutableList()
            blossombase[b] = blossombase[blossomchilds[b][0]]
            assert(blossombase[b] == v)
        }

        fun augmentMatching(k: Int) {
            val edge = edges[k]
            val v = edge.node1.toInt()
            val w = edge.node2.toInt()
            if (debugMode) println("augmentMatching($k) (v=$v w=$w)")
            if (debugMode) println("PAIR $v $w (k=$k)")
            val listPair = listOf(Pair(v, 2 * k + 1), Pair(w, 2 * k))
            listPair.forEach { (ls, lp) ->
                var s = ls
                var p = lp
                while (true) {
                    val bs = inblossom[s]
                    assert(label[bs] == 1)
                    // assert(labelend[bs] == mate[blossombase[bs]].toInt()) // SKETCHY commented
                    if (bs >= nvertex) {
                        augmentBlossom(bs, s)
                    }
                    mate[s] = p.toLong()
                    if (labelend[bs] == -1) {
                        break
                    }
                    val t = endpoint[labelend[bs]].toInt()
                    val bt = inblossom[t]
                    assert(label[bt] == 2)
                    assert(labelend[bt] >= 0)
                    s = endpoint[labelend[bt]].toInt()
                    val j = endpoint[labelend[bt].xor(1)].toInt()
                    assert(blossombase[bt] == t)
                    if (bt >= nvertex) {
                        augmentBlossom(bt, j)
                    }
                    mate[j] = labelend[bt].toLong()
                    p = labelend[bt].xor(1)
                    if (debugMode) println("PAIR $s $t (k=${(p / 2)})")
                }
            }
        }

        fun verifyOptimum() {
            // TODO
        }
        fun checkDelta2() {
            // TODO
        }
        fun checkDelta3() {
            // TODO
        }

        fun mainLoop(): MutableList<Long> {
            for (t in 0 until nvertex) {

                if (debugMode) println("STAGE $t")

                label.clear()
                for (i in 0 until nvertex) {
                    label.add(0)
                }
                for (i in 0 until nvertex) {
                    label.add(0)
                }

                bestedge.clear()
                for (i in 0 until nvertex) {
                    bestedge.add(-1)
                }
                for (i in 0 until nvertex) {
                    bestedge.add(-1)
                }

                for (i in 0 until nvertex) {
                    blossombestedges.removeLast()
                }
                for (i in 0 until nvertex) {
                    blossombestedges.add(mutableListOf())
                }

                allowedge.clear()
                for (i in 0 until nedges) {
                    allowedge.add(false)
                }

                queue.clear()

                for (v in 0 until nvertex) {
                    if (mate[v].toInt() == -1 && label[inblossom[v]] == 0) {
                        assignLabel(v, 1, -1)
                    }
                }

                var augmented = false

                while (true) {
                    if (debugMode) println("SUBSTAGE")
                    while (queue.isNotEmpty() && !augmented) {
                        val v = queue.removeLast()
                        if (debugMode) println("POP v=$v")
                        assert(label[inblossom[v]] == 1)

                        run whileCycle@{
                            neighbend[v].forEach neighbendCycle@{ p ->
                                val k = (p / 2)
                                val w = endpoint[p].toInt()
                                val kslack = slack(k)
                                if (inblossom[v] == inblossom[w]) {
                                    return@neighbendCycle
                                }
                                if (!allowedge[k]) {
                                    if (kslack <= 0) {
                                        allowedge[k] = true
                                    }
                                }
                                if (allowedge[k]) {
                                    if (label[inblossom[w]] == 0) {
                                        assignLabel(w, 2, p.xor(1))
                                    } else if (label[inblossom[w]] == 1) {
                                        val base = scanBlossom(v, w)
                                        if (base >= 0) {
                                            addBlossom(base, k)
                                        } else {
                                            augmentMatching(k)
                                            augmented = true
                                            return@whileCycle
                                        }
                                    } else if (label[w] == 0) {
                                        assert(label[inblossom[w]] == 2)
                                        label[w] = 2
                                        labelend[w] = p.xor(1)
                                    }
                                } else if (label[inblossom[w]] == 1) {
                                    val b = inblossom[v]
                                    if (bestedge[b] == -1 || kslack < slack(bestedge[b])) {
                                        bestedge[b] = k
                                    }
                                } else if (label[w] == 0) {
                                    if (bestedge[w] == -1 || kslack < slack(bestedge[w])) {
                                        bestedge[w] = k
                                    }
                                }
                            }
                        }
                    }
                    if (augmented) {
                        break
                    }

                    var deltatype = -1
                    var delta = 0
                    var deltaedge = 0
                    var deltablossom = 0

                    if (!maxcardinality) {
                        deltatype = 1
                        delta = dualvar.take(nvertex).minOf { it }.toInt()
                    }

                    for (v in 0 until nvertex) {
                        if (label[inblossom[v]] == 0 && bestedge[v] != -1) {
                            val d = slack(bestedge[v])
                            if (deltatype == -1 || d.toInt() < delta) {
                                delta = d.toInt()
                                deltatype = 2
                                deltaedge = bestedge[v]
                            }
                        }
                    }

                    for (b in 0 until (2 * nvertex)) {
                        if (blossomparent[b] == -1 && label[b] == 1 && bestedge[b] != -1) {
                            val kslack = slack(bestedge[b])
                            // A check about kslack being int or long, to review
                            assert((kslack % 2).toInt() == 0)
                            val d = kslack / 2
                            if (deltatype == -1 || d < delta) {
                                delta = d.toInt()
                                deltatype = 3
                                deltaedge = bestedge[b]
                            }
                        }
                    }

                    for (b in nvertex until (2 * nvertex)) {
                        if (blossombase[b] >= 0 && blossomparent[b] == -1 && label[b] == 2 &&
                            (deltatype == -1 || dualvar[b] < delta)
                        ) {
                            delta = dualvar[b].toInt()
                            deltatype = 4
                            deltablossom = b
                        }
                    }

                    if (deltatype == -1) {
                        assert(maxcardinality)
                        deltatype = 1
                        delta = max(0, dualvar.take(nvertex).minOf { it }.toInt())
                    }

                    for (v in 0 until nvertex) {
                        if (label[inblossom[v]] == 1) {
                            dualvar[v] = dualvar[v] - delta
                        } else if (label[inblossom[v]] == 2) {
                            dualvar[v] = dualvar[v] + delta
                        }
                    }

                    for (b in nvertex until (2 * nvertex)) {
                        if (blossombase[b] >= 0 && blossomparent[b] == -1) {
                            if (label[b] == 1) {
                                dualvar[b] = dualvar[b] + delta
                            } else if (label[b] == 2) {
                                dualvar[b] = dualvar[b] - delta
                            }
                        }
                    }

                    if (debugMode) println("delta$deltatype=$delta")
                    if (deltatype == 1) {
                        break
                    } else if (deltatype == 2) {
                        allowedge[deltaedge] = true
                        val edge = edges[deltaedge]
                        var i = edge.node1.toInt()
                        var j = edge.node2.toInt()
                        if (label[inblossom[i]] == 0) {
                            i = j.also { j = i }
                        }
                        assert(label[inblossom[i]] == 1)
                        queue.add(i)
                    } else if (deltatype == 3) {
                        allowedge[deltaedge] = true
                        val edge = edges[deltaedge]
                        val i = edge.node1.toInt()
                        assert(label[inblossom[i]] == 1)
                        queue.add(i)
                    } else if (deltatype == 4) {
                        expandBlossom(deltablossom, false)
                    }
                }
                if (!augmented) {
                    break
                }
                for (b in nvertex until (2 * nvertex)) {
                    if (blossomparent[b] == -1 && blossombase[b] >= 0 &&
                        label[b] == 1 && dualvar[b].toInt() == 0
                    ) {
                        expandBlossom(b, true)
                    }
                }
            }

            for (v in 0 until nvertex) {
                if (mate[v] >= 0) {
                    mate[v] = endpoint[mate[v].toInt()]
                }
            }
            for (v in 0 until nvertex) {
                assert(mate[v].toInt() == -1 || mate[mate[v].toInt()].toInt() == v)
            }

            return mate
        }

        return mainLoop()
    }
}
