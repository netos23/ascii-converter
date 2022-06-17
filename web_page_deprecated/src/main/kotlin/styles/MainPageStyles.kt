package styles

import kotlinx.css.LinearDimension
import kotlinx.css.margin
import styled.StyleSheet

object MainPageStyles : StyleSheet("main-page-styles", true) {
    val fileUploaderContainer by css {
        margin(
            top = LinearDimension("2vh"),
            left = LinearDimension("3vw"),
            right = LinearDimension("3vw")
        )
    }
}