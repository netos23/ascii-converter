package ru.fbtw.ascii_converter

import GifDecoder
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import ru.fbtw.ascii_converter.core.AsciiImgConverterConfiguration
import ru.fbtw.ascii_converter.core.MatchData
import ru.fbtw.ascii_converter.io.printAsciiImg
import ru.fbtw.ascii_converter.io.writeAsciiImgAsImage
import ru.fbtw.ascii_converter.io.writeGifAsciiImage
import java.awt.Color
import java.awt.Font
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import javax.imageio.ImageIO
import ru.fbtw.ascii_converter.core.extractByFrequency as extractors


internal class FastAsciiImgConverterTest {

    private val testFile = File("""assets/sticj.png""")
    private val gifFile = "assets/racon_an.gif"

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

        val decoder = GifDecoder()
        decoder.read(FileInputStream(gifFile))


        val img = ImageIO.read(testFile)
        val charImg = converter.convert(
            /*decoder.getFrame(4) ?:*/
            img,
            AsciiImgConverterConfiguration(200, 150),
        )
        Assertions.assertNotNull(charImg)
/*        writeAsciiImgAsHTML(
//            background = Color(java.awt.Color.WHITE.rgb),
            asciiImg = charImg,
            outputStream = FileOutputStream("target/test.html")
        )*/
        /* val image = writeAsciiImgAsImage(
 //            background = Color(java.awt.Color.WHITE.rgb),
             asciiImg = charImg,
             font = Font("Courier", Font.PLAIN, 20)
         )
         ImageIO.write(image,"png", File("assets/test.jpg"))
         */
    }

    @Test
    fun testConvertGif() {
        val converter = GifAsciiImageConverter(
            ColoredAsciiImageConverter(
                colorExtractor = { extractors(it) },
                FastAsciiImgConverter(
                    matcher = AverageCharMatcher(matchData),
                ),
            ),
        )

        val decoder = GifDecoder()
        decoder.read(FileInputStream(gifFile))

        val charImg = converter.convert(
            /*decoder.getFrame(4) ?:*/
            Array(decoder.frameCount) { decoder.getFrame(it)!! }.toList(),
            AsciiImgConverterConfiguration(100, 60),
        )
        Assertions.assertNotNull(charImg)
        writeGifAsciiImage(
            background = ru.fbtw.ascii_converter.utill.Color(Color.WHITE.rgb),
            asciiImg = charImg,
            outputStream = FileOutputStream("assets/test1.gif"),
            font = Font("Courier", Font.PLAIN, 12)
        )
/*        writeAsciiImgAsHTML(
//            background = Color(java.awt.Color.WHITE.rgb),
            asciiImg = charImg,
            outputStream = FileOutputStream("target/test.html")
        )*/
        /* val image = writeAsciiImgAsImage(
 //            background = Color(java.awt.Color.WHITE.rgb),
             asciiImg = charImg,
             font = Font("Courier", Font.PLAIN, 20)
         )
         ImageIO.write(image,"png", File("assets/test.jpg"))
         */
    }

    @Test
    fun convertReversed() {
        val converter = FastAsciiImgConverter(matcher = AverageCharMatcher(matchData))

        val charImg = converter.convert(ImageIO.read(testFile), AsciiImgConverterConfiguration(80, 45))
        val img = writeAsciiImgAsImage(

            asciiImg = charImg.map {
                it.map { it to ru.fbtw.ascii_converter.utill.Color(Color.WHITE.rgb) }.toTypedArray()
            }.toTypedArray(),
            font = Font("Courier", Font.PLAIN, 12),
            background = ru.fbtw.ascii_converter.utill.Color(255,255,255)
        )
        ImageIO.write(img,"png",FileOutputStream("./assets/tom_conv.png"))
//        printAsciiImg(charImg)
    }
}