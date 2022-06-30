package ru.fbtw.ascii_converter

import ru.fbtw.ascii_converter.core.*
import ru.fbtw.ascii_converter.utill.*
import java.awt.image.BufferedImage
import java.util.*
import kotlin.math.min
import kotlin.math.pow


class FastAsciiImgConverter(
    imgFilterChain: List<ImageFilter>? = null,
    colorFilterChain: List<ColorFilter>? = null,
    rgbFilterChain: List<RgbFilter>? = null,
    grayscaleFilterFilterChain: List<GrayscaleFilter>? = null,
    matcher: CharMatcher
) : AbstractAsciiImgConverter(imgFilterChain, colorFilterChain, rgbFilterChain, grayscaleFilterFilterChain, matcher) {

    override fun doImgFilter(img: BufferedImage): BufferedImage = img

    override fun extractColorMatrix(img: BufferedImage): Array<Array<Int>> = img.toMatrix()

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

class ColoredAsciiImageConverter<T>(
    private val colorExtractor: (SubMatrix<Int>) -> Color,
    private val asciiImgConverter: AsciiImgConverter<T>,
) : AsciiImgConverter<Pair<T, Color>> {

    override fun convert(img: BufferedImage, config: AsciiImgConverterConfiguration): Array<Array<Pair<T, Color>>> {
        assert(
            img.height > config.h
        ) {
            "Image resolution lower then ascii dimensions"
        }

        val imageMatrix = img.toMatrix()
        val segmentHeight = imageMatrix.size / config.h
        val segmentWidth = imageMatrix[0].size / config.w

        val colorMatrix = Array(config.h) { y ->
            Array(config.w) { x ->
                val remainingHeight = imageMatrix.size - y * segmentHeight
                val remainingWidth = imageMatrix[0].size - x * segmentWidth

                colorExtractor(
                    SubMatrix(
                        imageMatrix,
                        x * segmentWidth,
                        y * segmentHeight,
                        min(segmentWidth, remainingWidth),
                        min(segmentHeight, remainingHeight),
                    )
                )
            }
        }

        val charMatrix = asciiImgConverter.convert(img, config)

        return Array(config.h) { y ->
            Array(config.w) { x ->
                charMatrix[y][x] to colorMatrix[y][x]
            }
        }
    }
}

class GifAsciiImageConverter<T>(
    private val asciiImgConverter: AsciiImgConverter<T>,
) : GifImageConverter<T> {
    override fun convert(images: List<BufferedImage>, config: AsciiImgConverterConfiguration): List<Array<Array<T>>> =
        images.map { asciiImgConverter.convert(it, config) }
}

data class AverageMatchData(override val char: Char, override val predicate: Double) : MatchData<Double>

