import java.awt.image.BufferedImage
import java.io.File
import java.util.function.Predicate
import javax.imageio.ImageIO
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.roundToInt

fun main() {
    val path = """C:\Users\nikmo\IdeaProjects\prodaction\ascii-converter\lib\assets\tom.jpg"""
    val src = File(path)
    val img: BufferedImage = ImageIO.read(src)

    val colorMatrix = Array(img.height) { r -> img[r].toArray }
    val grayscaleMatrix = toGrayScale(colorMatrix)

    val asciiImg = toAsciiImage(
        gsMatrix = grayscaleMatrix,
        w = 80, h = 35,
        listOf(' ', '.', ',', ':', ';', '_', '-', '+', '*', 'a', '&', '#', '$', '@'),
    )

    printAsciiImg(asciiImg)
}

private fun printAsciiImg(asciiImg: Array<Array<Char>>) {
    asciiImg.forEach { chars ->
        val str = StringBuilder()
        chars.forEach { str.append(it) }
        println(str)
    }
}

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

            /* val rr = (r / 255.0)
             val gg = (g / 255.0)
             val bb = (b / 255.0)
 */
            // Calculate luminance:
            val lum = (0.2126 * rr + 0.7152 * gg + 0.0722 * bb)
            lum
        }
    }

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


operator fun BufferedImage.get(r: Int): ImageRow = ImageRow(this, r)

data class ImageRow(val img: BufferedImage, val r: Int) {
    val toArray: Array<Int>
        get() = Array(img.width) { c -> img[r][c] }

    operator fun get(c: Int) = img.getRGB(c, r)
    operator fun set(c: Int, rgb: Int) = img.setRGB(c, r, rgb)
}





