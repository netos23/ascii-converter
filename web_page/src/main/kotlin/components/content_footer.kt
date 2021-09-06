package components

import react.*

class ContentFooter : RComponent<RProps, RState>() {

    override fun RBuilder.render() {

    }

}

fun RBuilder.contentFooter(handler: RProps.() -> Unit): ReactElement =
    child(ContentFooter::class) {
        attrs.handler()
    }