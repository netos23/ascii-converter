package ru.fbtw.ascii_converter

import org.junit.jupiter.api.Test
import ru.fbtw.ascii_converter.*
import java.io.File
import javax.imageio.ImageIO

internal class FastAsciiImgConverterTest {

    val testFile = File("""assets/tom.jpg""")

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

    private val matchDataReversed = matchData.reversed()

    @Test
    fun convert() {
        val converter = FastAsciiImgConverter(matcher = AverageCharMatcher(matchData))

        val charImg = converter.convert(ImageIO.read(testFile), AsciiImgConverterConfiguration(80, 35))
        printAsciiImg(charImg)
    }

    @Test
    fun convertReversed() {
        val converter = FastAsciiImgConverter(matcher = AverageCharMatcher(matchDataReversed))

        val charImg = converter.convert(ImageIO.read(testFile), AsciiImgConverterConfiguration(80, 35))
        printAsciiImg(charImg)
    }
}