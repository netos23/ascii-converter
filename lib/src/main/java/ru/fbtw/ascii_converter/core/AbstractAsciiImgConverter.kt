package ru.fbtw.ascii_converter.core

import ru.fbtw.ascii_converter.utill.Color
import ru.fbtw.ascii_converter.utill.ColorFilter
import ru.fbtw.ascii_converter.utill.GrayscaleFilter
import ru.fbtw.ascii_converter.utill.ImageFilter
import ru.fbtw.ascii_converter.utill.RgbFilter
import ru.fbtw.ascii_converter.utill.SubMatrix
import java.awt.image.BufferedImage
import kotlin.math.min

abstract class AbstractAsciiImgConverter(
    protected val imgFilterChain: List<ImageFilter>? = null,
    protected val colorFilterChain: List<ColorFilter>? = null,
    protected val rgbFilterChain: List<RgbFilter>? = null,
    protected val grayscaleFilterFilterChain: List<GrayscaleFilter>? = null,
    protected val matcher: CharMatcher
) : AsciiImgConverter<Char> {

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