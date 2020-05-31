package example.route

import example.controller.ArtistController
import example.controller.Auth0Controller
import example.controller.FirebaseController
import example.controller.HelloController
import example.logger
import example.request.PostRequest
import example.response.PostResponse
import io.ktor.application.ApplicationCallPipeline
import io.ktor.application.call
import io.ktor.auth.authenticate
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

@KtorExperimentalLocationsAPI
fun Routing.root() {
    val artistController: ArtistController by inject()
    val helloController: HelloController by inject()
    val auth0Controller: Auth0Controller by inject()
    val firebaseController: FirebaseController by inject()

    @KtorExperimentalLocationsAPI
    @Location("/{id}")
    data class IdParam(
        val id: Int
    )

    get<IdParam> {
        val id = it.id
        call.respond(id)
    }

    @KtorExperimentalLocationsAPI
    @Location("/{value}")
    data class StringParam(
        val value: String
    )

    // favicon のエラーログが出るので追記
    get<StringParam> {}

    get("/") {
        call.respond(HttpStatusCode.OK, "Hello World")
        logger().info("info log")
    }

    post("/") {
        val request = call.receive<PostRequest>()
        call.respond(PostResponse("${request.value} posted"))
    }

    authenticate("jwt") {
        get("/hello") {
            call.respond(helloController.hello())
        }
    }

    authenticate("token") {
        get("/auth0/token") {
            call.respond(auth0Controller.getAccessToken())
        }
    }

    get("/auth0/token") {
        call.respond(auth0Controller.getAccessToken())
    }

    @Location("/firebase/token/{uid}")
    data class FirebaseUidParam(
        val uid: String
    )

    /**
     * firebase のトークンを取得する
     */
    get<FirebaseUidParam> {
        val result = firebaseController.getToken(it.uid)
        call.respond(HttpStatusCode.OK, result)
    }

    /**
     * firebase の認証が必要なエンドポイント
     */
    authenticate("firebase") {
        get("/firebase/auth") {
            call.respond(HttpStatusCode.OK, "firebase auth")
        }
    }

    route("/artists") {
        intercept(ApplicationCallPipeline.Features) {
            println("this is artists api call.")
        }

        // locations で path を指定する
        @KtorExperimentalLocationsAPI
        @Location("/{artistId}")
        data class ArtistIdParam(
            val artistId: Int
        )

        get {
            call.respond(artistController.all())
        }

        // io.ktor.localtions.get を使用する
        get<ArtistIdParam> {
            call.respond(artistController.getArtistById(it.artistId))
        }

        post {
            artistController.create(call.receive())
            call.respond(HttpStatusCode.Created)
        }
    }
}
