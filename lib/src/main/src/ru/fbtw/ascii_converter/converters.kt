package ru.fbtw.ascii_converter

import java.awt.image.BufferedImage
import java.util.*
import kotlin.math.min
import kotlin.math.pow


interface AsciiImgConverter {
    fun convert(img: BufferedImage, config: AsciiImgConverterConfiguration): Array<Array<Char>>
}

abstract class AbstractAsciiImgConverter(
    protected val imgFilterChain: List<ImageFilter>? = null,
    protected val colorFilterChain: List<ColorFilter>? = null,
    protected val rgbFilterChain: List<RgbFilter>? = null,
    protected val grayscaleFilterFilterChain: List<GrayscaleFilter>? = null,
    protected val matcher: CharMatcher
) : AsciiImgConverter {

    protected abstract fun doImgFilter(img: BufferedImage): BufferedImage

    protected fun doImgFilterInternal(img: BufferedImage): BufferedImage {
        val filteredImage = doImgFilter(img)
        return imgFilterChain?.fold(filteredImage) { accImage, filter -> filter(accImage) } ?: filteredImage
    }

    protected abstract fun extractColorMatrix(img: BufferedImage): Array<Array<Int>>

    protected abstract fun doColorFilter(color: Int): Int

    protected fun doColorFilterInternal(color: Int): Int {
        val tmpColor = doColorFilter(color)
        return colorFilterChain?.fold(tmpColor) { accCol, filter -> filter(accCol) } ?: tmpColor
    }

    protected abstract fun extractRgb(color: Array<Array<Int>>): Array<Array<Color>>

    protected abstract fun doRgbFilter(color: Color): Color

    protected fun doRgbFilterInternal(color: Color): Color {
        val tmpRgb = doRgbFilter(color)
        return rgbFilterChain?.fold(tmpRgb) { accCol, filter -> filter(accCol) } ?: tmpRgb
    }

    protected abstract fun convertToGrayScale(color: Array<Array<Color>>): Array<Array<Double>>

    protected abstract fun doGrayscaleFilter(gs: Double): Double

    protected fun doGrayscaleFilterInternal(gs: Double): Double {
        val tmpGs = doGrayscaleFilter(gs)
        return grayscaleFilterFilterChain?.fold(tmpGs) { accGs, filter -> filter(accGs) } ?: tmpGs
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

        return Array(config.h) { y ->
            Array(config.w) { x ->
                val remainingHeight = grayscaleMatrix.size - y * segmentHeight
                val remainingWidth = grayscaleMatrix[0].size - x * segmentWidth

                applyMatcher(
                    SubMatrix(
                        grayscaleMatrix,
                        x * segmentWidth,
                        y * segmentHeight,
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
    matcher: CharMatcher
) : AbstractAsciiImgConverter(imgFilterChain, colorFilterChain, rgbFilterChain, grayscaleFilterFilterChain, matcher) {

    override fun doImgFilter(img: BufferedImage): BufferedImage = img

    override fun extractColorMatrix(img: BufferedImage): Array<Array<Int>> = Array(img.height) { row ->
        Array(img.width) { col: Int ->
            img[row][col]
        }
    }

    override fun doColorFilter(color: Int): Int = color

    override fun extractRgb(color: Array<Array<Int>>): Array<Array<Color>> = Array(color.size) { row ->
        Array(color[0].size) { col: Int ->
            Color(color[row][col])
        }
    }

    override fun doRgbFilter(color: Color): Color = color

    override fun convertToGrayScale(color: Array<Array<Color>>): Array<Array<Double>> = Array(color.size) { row ->
        Array(color[0].size) { col: Int ->
            val r = (color[row][col].r / 255.0).pow(2.2)
            val g = (color[row][col].g / 255.0).pow(2.2)
            val b = (color[row][col].b / 255.0).pow(2.2)

            val lum = (0.2126 * r + 0.7152 * g + 0.0722 * b)

            lum
        }
    }

    override fun doGrayscaleFilter(gs: Double): Double = gs

}

data class AsciiImgConverterConfiguration(val w: Int, val h: Int, val allowedChars: SortedSet<Char>? = null)

interface CharMatcher {
    fun prepare(allowedChars: Set<Char>?)
    fun match(segment: SubMatrix<Double>): Char
}

abstract class AbstractCharMatcher<P : Comparable<P>> : CharMatcher {
    abstract val charset: List<MatchData<P>>
    abstract val isReady: Boolean
}

class AverageCharMatcher(override val charset: List<MatchData<Double>>) :
    AbstractCharMatcher<Double>() {

    private lateinit var workingChars: TreeMap<Int, Char>

    override val isReady: Boolean
        get() = this::workingChars.isInitialized

    override fun prepare(allowedChars: Set<Char>?) {
        if (isReady) {
            workingChars.clear()
        } else {
            workingChars = TreeMap()
        }

        workingChars.putAll(charset.filter { allowedChars?.contains(it.char) ?: true }
            .associate { matchData -> matchData.predicate.toPercent() to matchData.char })
    }

    override fun match(segment: SubMatrix<Double>): Char {
        var sumLum = 0.0
        for (row in segment) {
            for (lum in row) {
                sumLum += lum
            }
        }

        val avgLum = sumLum / segment.area

        val lumPercent = avgLum.toPercent()


        return workingChars.floorEntry(lumPercent).value
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
            (y in matrix.indices) and (y + h in matrix.indices)
                    and (x in matrix[0].indices) and (x + w in matrix[0].indices)
        ) {
            "One or more arg mismatch bounds. " +
                    "Bounds: ${matrix.size} * ${matrix[0].size}, x: $x, y: $y, w: $w, h: $h"
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

fun Double.toPercent() = (this * 100).toInt()

fun printAsciiImg(asciiImg: Array<Array<Char>>) {
    asciiImg.forEach { chars ->
        val str = StringBuilder()
//        chars.forEach { str.append("${it.code} ") }
        chars.forEach { str.append(it) }
        println(str)
    }
}