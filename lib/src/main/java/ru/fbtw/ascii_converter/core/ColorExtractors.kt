package ru.fbtw.ascii_converter.core

import ru.fbtw.ascii_converter.utill.Color
import ru.fbtw.ascii_converter.utill.SubMatrix
import java.util.*

fun extractByFrequency(subMatrix: SubMatrix<Int>): Color {
    val frequencyMap = TreeMap<Int, Int>()

    for (row in subMatrix) {
        for (element in row) {
            val count = frequencyMap.getOrDefault(element, 0) + 1
            frequencyMap[element] = count
        }
    }

    return Color(frequencyMap.lastKey())
}