import components.App
import kotlinx.browser.document
import kotlinx.browser.window
import react.dom.render

fun main() {
    window.onload = {
        render(document.getElementById("root")) {
            child(App::class) {}
        }
    }


}
