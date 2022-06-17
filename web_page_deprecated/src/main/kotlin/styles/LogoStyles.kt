package styles

import kotlinx.css.*
import styled.StyleSheet

object LogoStyles : StyleSheet("logo-styles", true) {
    val logo by css {
        color = Color.white
        // TODO: 04.09.2021 add relative design
        fontSize = LinearDimension("3em")
        textAlign = TextAlign.center
    }

    val about by css {
        color = Color.white
        // TODO: 04.09.2021 add relative design
        fontSize = LinearDimension("1em")
        textAlign = TextAlign.center
    }
}