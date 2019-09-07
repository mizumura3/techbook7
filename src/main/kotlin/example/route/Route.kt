package example.route

import example.controller.ArtistController
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.locations.get
import io.ktor.routing.post
import io.ktor.routing.route
import org.koin.ktor.ext.inject

// locations で path を指定する
@KtorExperimentalLocationsAPI
@Location("/{artistId}")
data class ArtistIdParam(
    val artistId: Int
)

@KtorExperimentalLocationsAPI
fun Routing.root() {
    val artistController: ArtistController by inject()

    get("/") {
        call.respond(HttpStatusCode.OK, "Hello World")
    }

    // io.ktor.localtions.get を使用する
    get<ArtistIdParam> {
        call.respond(artistController.getArtist(it.artistId))
    }

    route("/users") {

        get {
            call.respond(artistController.all())
        }

        post {
            artistController.create(call.receive())
            call.respond(HttpStatusCode.OK)
        }
    }
}
