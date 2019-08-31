package example.route

import example.controller.ArtistController
import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.route
import org.koin.ktor.ext.inject

fun Routing.root() {
    val artistController: ArtistController by inject()
    route("/users") {
        get {
            call.respond(artistController.all())
        }
    }
}