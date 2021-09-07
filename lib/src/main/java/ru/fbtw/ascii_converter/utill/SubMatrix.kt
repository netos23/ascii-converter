package ru.fbtw.ascii_converter.utill

data class SubMatrix<T>(val matrix: Array<Array<T>>, val x: Int, val y: Int, val w: Int, val h: Int) :
    Iterable<Iterable<T>> {

    val area: Int = w * h

    init {
        require(matrix.isNotEmpty() and matrix.all(Array<T>::isNotEmpty)) {
            "Empty matrix. All bounds must be greater than 0"
        }

        val width = matrix[0].size
        require(matrix.fold(true) { acc, p -> (p.size == width) and acc }) {
            "Only rectangle matrix allowed"
        }

        require(
            (y in matrix.indices) and (y + h - 1 in matrix.indices)
                    and (x in matrix[0].indices) and (x + w - 1 in matrix[0].indices)
        ) {
            "One or more arg mismatch bounds. " +
                    "Bounds: ${matrix.size} * ${matrix[0].size}, x: $x, y: $y, w: $w, h: $h"
        }
    }

    constructor(matrix: Array<Array<T>>) : this(
        matrix,
        x = 0, y = 0,
        w = if (matrix.isNotEmpty()) matrix[0].size else 0,
        h = matrix.size
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SubMatrix<*>

        if (!matrix.contentDeepEquals(other.matrix)) return false
        if (x != other.x) return false
        if (y != other.y) return false
        if (w != other.w) return false
        if (h != other.h) return false

        return true
    }

    override fun hashCode(): Int {
        var result = matrix.contentDeepHashCode()
        result = 31 * result + x
        result = 31 * result + y
        result = 31 * result + w
        result = 31 * result + h
        return result
    }

    override fun iterator(): Iterator<Iterable<T>> = object : Iterator<Iterable<T>> {
        private var yIndex = y

        override fun hasNext(): Boolean = yIndex < y + h
        override fun next(): Iterable<T> = object : Iterable<T> {

            override fun iterator(): Iterator<T> = object : Iterator<T> {
                private val row = yIndex++
                private var col = x

                override fun hasNext(): Boolean = col < x + w
                override fun next(): T = matrix[row][col++]
            }
        }
    }
}