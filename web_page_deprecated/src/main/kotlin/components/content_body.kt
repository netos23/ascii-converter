package components

import react.*
import styled.css
import styled.styledDiv
import styles.MainPageStyles

class ContentBody : RComponent<RProps, RState>() {

    override fun RBuilder.render() {
        logo {  }
        about {  }
        // FileUploader container
        styledDiv {
            css {
                +MainPageStyles.fileUploaderContainer
            }
            fileUploader { }
        }

    }

}

fun RBuilder.contentBody(handler: RProps.() -> Unit): ReactElement =
    child(ContentBody::class) {
        attrs.handler()
    }