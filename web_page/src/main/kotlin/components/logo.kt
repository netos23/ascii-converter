package components

import react.*
import styled.css
import styled.styledH1
import styles.LogoStyles

class Logo : RComponent<RProps, RState>() {
    override fun RBuilder.render() {
        styledH1 {
            css {
                +LogoStyles.logo
            }
            +"ASCII CONVERTER"
        }
    }
}

fun RBuilder.logo(handler: RProps.() -> Unit): ReactElement =
    child(Logo::class) {
        attrs.handler()
    }