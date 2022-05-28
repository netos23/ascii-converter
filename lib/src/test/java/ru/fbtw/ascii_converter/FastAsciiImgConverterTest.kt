package ru.fbtw.ascii_converter

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import printAsciiImg
import printAsciiImgAsHTML
import ru.fbtw.ascii_converter.core.AsciiImgConverterConfiguration
import ru.fbtw.ascii_converter.core.MatchData
import ru.fbtw.ascii_converter.utill.Color
import java.io.File
import java.io.FileOutputStream
import javax.imageio.ImageIO
import ru.fbtw.ascii_converter.core.extractByFrequency as extractors

internal class FastAsciiImgConverterTest {

    private val testFile = File("""assets/racon.jpeg""")

    // ' ', '.', ',', ':', ';', '_', '-', '+', '*', 'a', '&', '#', '$', '@'

    private val matchData = listOf<MatchData<Double>>(
        AverageMatchData(' ', 0.0),
        AverageMatchData('.', 0.04),
        AverageMatchData(',', 0.08),
        AverageMatchData(':', 0.12),
        AverageMatchData(';', 0.15),
        AverageMatchData('_', 0.18),
        AverageMatchData('-', 0.21),
        AverageMatchData('+', 0.27),
        AverageMatchData('*', 0.31),
        AverageMatchData('a', 0.4),
        AverageMatchData('&', 0.6),
        AverageMatchData('#', 0.7),
        AverageMatchData('$', 0.8),
        AverageMatchData('@', 0.9),
    )


    private val matchDataReversed = Array<MatchData<Double>>(matchData.size) { i ->
        AverageMatchData(matchData[i].char, matchData[matchData.size - 1 - i].predicate)
    }.toList()

    @Test
    fun convert() {
        val converter = FastAsciiImgConverter(matcher = AverageCharMatcher(matchData))

        val charImg = converter.convert(ImageIO.read(testFile), AsciiImgConverterConfiguration(500, 200))
        Assertions.assertNotNull(charImg)
        printAsciiImg(charImg)
    }


    @Test
    fun convertColored() {
        val converter = ColoredAsciiImageConverter(
            colorExtractor = { extractors(it) },
            FastAsciiImgConverter(
                matcher = AverageCharMatcher(matchDataReversed),
            ),
        )


        val charImg = converter.convert(ImageIO.read(testFile), AsciiImgConverterConfiguration(200, 150))
        Assertions.assertNotNull(charImg)
        printAsciiImgAsHTML(
            background = Color(java.awt.Color.WHITE.rgb),
            asciiImg = charImg,
            outputStream = FileOutputStream("target/test.html")
        )
    }

    @Test
    fun convertReversed() {
        val converter = FastAsciiImgConverter(matcher = AverageCharMatcher(matchDataReversed))

        val charImg = converter.convert(ImageIO.read(testFile), AsciiImgConverterConfiguration(80, 35))
        printAsciiImg(charImg)
    }
}