import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.roundToInt

@Deprecated(message = "There is just a prototype, Use AsciiImgConverters instead")
fun main() {
    val path = """assets/tom.jpg"""
    val src = File(path)
    val img: BufferedImage = ImageIO.read(src)

    val colorMatrix = Array(img.height) { r -> img[r].toArray }
    val grayscaleMatrix = toGrayScale(colorMatrix)

    val asciiImg = toAsciiImage(
        grayscaleMatrix,
        w = 80, h = 35,
        listOf(' ', '.', ',', ':', ';', '_', '-', '+', '*', 'a', '&', '#', '$', '@'),
    )

    printAsciiImg(asciiImg)
}

@Deprecated(message = "There is just a prototype, Use AsciiImgConverters instead")
fun toGrayScale(colorMatrix: Array<Array<Int>>): Array<Array<Double>> =
    Array(colorMatrix.size) { row ->
        Array(colorMatrix[row].size) { col: Int ->
            // Extract r,g,b
            val color = colorMatrix[row][col]
            val r: Int = color shr 16 and 0xFF
            val g: Int = color shr 8 and 0xFF
            val b: Int = color and 0xFF

            // Normalize and gamma correct:
            val rr = (r / 255.0).pow(2.2)
            val gg = (g / 255.0).pow(2.2)
            val bb = (b / 255.0).pow(2.2)

            // Calculate luminance:
            val lum = (0.2126 * rr + 0.7152 * gg + 0.0722 * bb)
            lum
        }
    }

@Deprecated(message = "There is just a prototype, Use AsciiImgConverters instead")
fun toAsciiImage(gsMatrix: Array<Array<Double>>, w: Int, h: Int, chars: List<Char>): Array<Array<Char>> {
    val segH = gsMatrix.size / h
    val segW = gsMatrix[0].size / w
    val segArea = segH * segW

    return Array(h) { row ->
        Array(w) { col ->
            var avgLum = 0.0
            for (y in 0 until segH) {
                for (x in 0 until segW) {
                    val absX = x + col * segW
                    val absY = y + row * segH

                    if ((absX in gsMatrix[0].indices) and (absY in gsMatrix.indices)) {
                        avgLum += gsMatrix[absY][absX]
                    }
                }
            }

            avgLum /= segArea

            val index = min((avgLum * chars.size).roundToInt(), chars.size - 1)
            chars[index]
        }
    }
}





