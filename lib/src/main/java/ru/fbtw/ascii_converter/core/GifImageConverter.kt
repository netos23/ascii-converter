package ru.fbtw.ascii_converter.core

import java.awt.image.BufferedImage

interface GifImageConverter<T>{
    fun convert(images: List<BufferedImage>, config: AsciiImgConverterConfiguration): List<Array<Array<T>>>
}