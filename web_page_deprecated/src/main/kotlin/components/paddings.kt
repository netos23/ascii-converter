package components

import react.*
import styled.css
import styled.styledDiv
import styles.CommonStyles

class SmallPadding : RComponent<RProps, RState>() {

    override fun RBuilder.render() {
        styledDiv {
            css {
                +CommonStyles.smallPadding
            }
        }
    }

}

fun RBuilder.smallPadding(handler: RProps.() -> Unit): ReactElement =
    child(SmallPadding::class) {
        attrs.handler()
    }