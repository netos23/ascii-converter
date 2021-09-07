package styles

import kotlinx.css.*
import styled.StyleSheet

object FileUploaderStyles : StyleSheet("file-uploader", isStatic = true) {

    val fileInput by css {

    }

    val body by css {
        borderStyle = BorderStyle.dashed
        borderColor = Color.black

        padding = "5px"
    }

    val externalBorder by css {
        backgroundColor = rgba(0,100,0,0.7)

        // TODO: 04.09.2021 add relative design
        padding = "10px"
    }
}