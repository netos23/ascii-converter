package ru.fbtw.ascii_converter

import java.awt.image.BufferedImage
import java.util.*
import kotlin.math.min


interface AsciiImgConverter {
    fun convert(img: BufferedImage, config: AsciiImgConverterConfiguration): Array<Array<Char>>
}

abstract class AbstractAsciiImgConverter(
    protected val imgFilterChain: List<ImageFilter>? = null,
    protected val colorFilterChain: List<ColorFilter>? = null,
    protected val rgbFilterChain: List<RgbFilter>? = null,
    protected val grayscaleFilterFilterChain: List<GrayscaleFilter>? = null,
    protected val matcher: CharMatcher<Comparable<Any>>
) : AsciiImgConverter {

    protected abstract fun doImgFilter(img: BufferedImage): BufferedImage

    protected fun doImgFilterInternal(img: BufferedImage): BufferedImage {
        val filteredImage = doImgFilter(img)
        return imgFilterChain?.fold(filteredImage, { accImage, filter -> filter(accImage) }) ?: filteredImage
    }

    protected abstract fun extractColorMatrix(img: BufferedImage): Array<Array<Int>>

    protected abstract fun doColorFilter(color: Int): Int

    protected fun doColorFilterInternal(color: Int): Int {
        val tmpColor = doColorFilter(color)
        return colorFilterChain?.fold(tmpColor, { accCol, filter -> filter(accCol) }) ?: tmpColor
    }

    protected abstract fun extractRgb(color: Array<Array<Int>>): Array<Array<Color>>

    protected abstract fun doRgbFilter(color: Color): Color

    protected fun doRgbFilterInternal(color: Color): Color {
        val tmpRgb = doRgbFilter(color)
        return rgbFilterChain?.fold(tmpRgb, { accCol, filter -> filter(accCol) }) ?: tmpRgb
    }

    protected abstract fun convertToGrayScale(color: Array<Array<Color>>): Array<Array<Double>>

    protected abstract fun doGrayscaleFilter(gs: Double): Double

    protected fun doGrayscaleFilterInternal(gs: Double): Double {
        val tmpGs = doGrayscaleFilter(gs)
        return grayscaleFilterFilterChain?.fold(tmpGs, { accGs, filter -> filter(accGs) }) ?: tmpGs
    }

    protected fun applyMatcher(matrix: SubMatrix<Double>): Char = matcher.match(matrix)

    override fun convert(img: BufferedImage, config: AsciiImgConverterConfiguration): Array<Array<Char>> {
        matcher.prepare(config.allowedChars)

        val filteredImage = doImgFilterInternal(img)

        val colorMatrix = extractColorMatrix(filteredImage)
        for ((y, row) in colorMatrix.withIndex()) {
            for ((x, color) in row.withIndex()) {
                colorMatrix[y][x] = doColorFilterInternal(color)
            }
        }

        val rgbMatrix = extractRgb(colorMatrix)
        for ((y, row) in rgbMatrix.withIndex()) {
            for ((x, rgb) in row.withIndex()) {
                rgbMatrix[y][x] = doRgbFilterInternal(rgb)
            }
        }

        val grayscaleMatrix = convertToGrayScale(rgbMatrix)
        for ((y, row) in grayscaleMatrix.withIndex()) {
            for ((x, gs) in row.withIndex()) {
                grayscaleMatrix[y][x] = doGrayscaleFilterInternal(gs)
            }
        }

        val segmentHeight = grayscaleMatrix.size / config.h
        val segmentWidth = grayscaleMatrix[0].size / config.w

        return Array(config.h) { x ->
            Array(config.w) { y ->
                val remainingHeight = grayscaleMatrix.size - y * segmentHeight
                val remainingWidth = grayscaleMatrix[0].size - x * segmentWidth

                applyMatcher(
                    SubMatrix(
                        grayscaleMatrix,
                        x,
                        y,
                        min(segmentWidth, remainingWidth),
                        min(segmentHeight, remainingHeight)
                    )
                )
            }
        }
    }
}

class FastAsciiImgConverter(
    imgFilterChain: List<ImageFilter>? = null,
    colorFilterChain: List<ColorFilter>? = null,
    rgbFilterChain: List<RgbFilter>? = null,
    grayscaleFilterFilterChain: List<GrayscaleFilter>? = null,
    matcher: CharMatcher<Comparable<Any>>
) : AbstractAsciiImgConverter(imgFilterChain, colorFilterChain, rgbFilterChain, grayscaleFilterFilterChain, matcher) {

    override fun doImgFilter(img: BufferedImage): BufferedImage {
        TODO("Not yet implemented")
    }

    override fun extractColorMatrix(img: BufferedImage): Array<Array<Int>> {
        TODO("Not yet implemented")
    }

    override fun doColorFilter(color: Int): Int {
        TODO("Not yet implemented")
    }

    override fun extractRgb(color: Array<Array<Int>>): Array<Array<Color>> {
        TODO("Not yet implemented")
    }

    override fun doRgbFilter(color: Color): Color {
        TODO("Not yet implemented")
    }

    override fun convertToGrayScale(color: Array<Array<Color>>): Array<Array<Double>> {
        TODO("Not yet implemented")
    }

    override fun doGrayscaleFilter(gs: Double): Double {
        TODO("Not yet implemented")
    }

}

data class AsciiImgConverterConfiguration(val w: Int, val h: Int, val allowedChars: SortedSet<Char>)

interface CharMatcher<P : Comparable<P>> {
    val charset: List<MatchData<P>>
    val isReady: Boolean
    fun prepare(allowedChars: SortedSet<Char>)
    fun match(segment: SubMatrix<Double>): Char
}

class AverageCharMatcher(override val charset: List<MatchData<Double>>) :
    CharMatcher<Double> {

    private lateinit var workingChars: TreeMap<Int, Char>

    override val isReady: Boolean
        get() = this::workingChars.isInitialized

    override fun prepare(allowedChars: SortedSet<Char>) {
        if (isReady) {
            workingChars.clear()
        } else {
            workingChars = TreeMap()
        }

        workingChars.putAll(charset.filter { allowedChars.contains(it.char) }
            .associate { matchData -> (matchData.predicate * 100).toInt() to matchData.char })
    }

    override fun match(segment: SubMatrix<Double>): Char {
        
        for (row in segment) {
            for (lum in row) {

            }
        }

        TODO()
    }
}

interface MatchData<P : Comparable<P>> {
    val char: Char
    val predicate: P
}

data class AverageMatchData(override val char: Char, override val predicate: Double) : MatchData<Double>

typealias ImageFilter = (BufferedImage) -> BufferedImage
typealias ColorFilter = (Int) -> Int
typealias RgbFilter = (Color) -> Color
typealias GrayscaleFilter = (Double) -> Double

data class Color(val r: Int, val g: Int, val b: Int) {
    constructor(color: Int) : this(
        r = color shr 16 and 0xFF,
        g = color shr 8 and 0xFF,
        b = color and 0xFF,
    )
}

data class SubMatrix<T>(val matrix: Array<Array<T>>, val x: Int, val y: Int, val w: Int, val h: Int) :
    Iterable<Iterable<T>> {
    init {
        require(matrix.isNotEmpty() and matrix.all(Array<T>::isNotEmpty)) {
            "Empty matrix. All bounds must be greater than 0"
        }

        val width = matrix[0].size
        require(matrix.fold(true, { acc, p -> (p.size == width) and acc })) {
            "Only rectangle matrix allowed"
        }

        require((x in matrix.indices) and (x + w in matrix.indices)) {
            "One or more arg mismatch bounds. " +
                    "Bounds: ${matrix.size} x ${matrix[0].size}, x: $x, y: $y, w: $w, h: $h"
        }
    }

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

operator fun BufferedImage.get(r: Int): ImageRow = ImageRow(this, r)

data class ImageRow(val img: BufferedImage, val r: Int) {
    val toArray: Array<Int>
        get() = Array(img.width) { c -> img[r][c] }

    operator fun get(c: Int) = img.getRGB(c, r)
    operator fun set(c: Int, rgb: Int) = img.setRGB(c, r, rgb)
}


