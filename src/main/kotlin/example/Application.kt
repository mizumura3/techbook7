package example

import com.auth0.jwk.UrlJwkProvider
import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.datatype.joda.JodaModule
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import example.common.LocalDateSerializer
import example.exception.ExceptionResponse
import example.exception.RecordNotFoundException
import example.route.root
import example.token.TokenPrincipal
import example.token.firebaseJwt
import example.token.token
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.auth.jwt.JWTPrincipal
import io.ktor.auth.jwt.jwt
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.features.StatusPages
import io.ktor.http.HttpStatusCode
import io.ktor.jackson.jackson
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Locations
import io.ktor.response.respond
import io.ktor.routing.routing
import io.ktor.util.KtorExperimentalAPI
import org.jetbrains.exposed.sql.Database
import org.joda.time.LocalDate
import org.koin.ktor.ext.Koin
import org.koin.ktor.ext.getProperty

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

@KtorExperimentalAPI
@Suppress("unused") // Referenced in application.conf
@KtorExperimentalLocationsAPI
fun Application.module() {
    install(DefaultHeaders)
    install(CallLogging)
    install(Locations)
    install(ContentNegotiation) {
        jackson {
            // 設定はこの中に記述する
            registerModule(
                JodaModule().apply {
                    addSerializer(
                        LocalDate::class.java,
                        LocalDateSerializer()
                    )
                }
            )
        }
    }

    // Koin
    install(Koin) {
        // TODO ktor から設定読み込んで koin の property に設定する方法 今は使わない
        // koin.setProperty("db.user", environment.config.property("db.user").getString())
        koin.setProperty("auth0.client_id", environment.config.property("auth0.client_id").getString())
        koin.setProperty("auth0.client_secret", environment.config.property("auth0.client_secret").getString())
        koin.setProperty("auth0.audience", environment.config.property("auth0.audience").getString())
        koin.setProperty("firebase.apiKey", environment.config.property("firebase.apiKey").getString())

        // こうやって書くと koin.properties を読んでから System.getEnv で環境変数を読んで格納するので
        // 同じキー名の環境変数があった場合は上書きする
        fileProperties()
        environmentProperties()
        modules(sampleModule)
    }

    // Koin の install が終わった後に Ktor が Koin の getProperty() を使用可能になる
    println(getProperty("sample", ""))

    // StatusPages
    install(StatusPages) {
        // json の parse に失敗した場合は BadRequest を返却する
        exception<JsonParseException> {
            call.respond(HttpStatusCode.BadRequest, it.message!!)
            throw it // スローすると stacktrace をログに出力する
        }

        // RecordNotFoundException が発生した場合は404とエラーメッセージを返却する
        exception<RecordNotFoundException> {
            call.respond(HttpStatusCode.NotFound, ExceptionResponse(it.message!!))
            throw it // スローすると stacktrace をログに出力する
        }
    }

    val domain = environment.config.property("jwt.domain").getString()
    val audience = environment.config.property("jwt.audience").getString()
    val jwtRealm = environment.config.property("jwt.realm").getString()

    install(Authentication) {
        jwt("jwt") {
            realm = jwtRealm
            verifier(UrlJwkProvider(domain))
            validate { credential ->
                if (credential.payload.audience.contains(audience)) JWTPrincipal(credential.payload) else null
            }
        }

        token("token") {
            val token = environment.config.property("auth.token").getString()
            realm = "test"

            validate { credential ->
                if (credential.token == token) TokenPrincipal(token) else null
            }
        }

        firebaseJwt("firebase") { // Firebase のカスタム認証を追加
            projectId = "techbook8-fb629"
        }
    }

    // TODO getProperty で外部変数にすること
    Database.connect(
        "jdbc:mysql://127.0.0.1:3306/example?useSSL=false&serverTimezone=Asia/Tokyo",
        driver = getProperty("db.driver", ""),
        user = getProperty("db.user", ""),
        password = getProperty("db.password", "")
    )

    // これだけでカスタムトークン作れるのすごいな
    FirebaseApp.initializeApp()
    val token = FirebaseAuth.getInstance().createCustomToken("SLhMgCvzE0e2lOywBCq7695SogU2")

    println(token)


    routing {
        root()
    }
}
