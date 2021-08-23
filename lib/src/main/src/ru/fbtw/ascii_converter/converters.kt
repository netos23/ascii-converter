package ru.fbtw.ascii_converter

import ru.fbtw.ascii_converter.core.AbstractAsciiImgConverter
import ru.fbtw.ascii_converter.core.AbstractCharMatcher
import ru.fbtw.ascii_converter.core.CharMatcher
import ru.fbtw.ascii_converter.core.MatchData
import ru.fbtw.ascii_converter.utill.*
import java.awt.image.BufferedImage
import java.util.*
import kotlin.math.pow


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

data class AverageMatchData(override val char: Char, override val predicate: Double) : MatchData<Double>

