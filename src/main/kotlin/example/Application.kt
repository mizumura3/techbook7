package com.example

import example.domain.Artist
import example.module
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.jackson.jackson
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.routing
import org.jetbrains.exposed.sql.Database
import org.joda.time.LocalDate
import org.koin.ktor.ext.Koin

fun main(args: Array<String>) {

    // TODO getProperty で外部変数にすること
    Database.connect(
        "jdbc:mysql://127.0.0.1:3306/example?useSSL=false&serverTimezone=Asia/Tokyo",
        driver = "com.mysql.cj.jdbc.Driver",
        user = "root",
        password = "password"
    )

    io.ktor.server.netty.EngineMain.main(args)
}

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(DefaultHeaders)
    install(CallLogging)
    install(ContentNegotiation) {
        jackson {
            // 設定はこの中に記述する
        }
    }

    install(Koin) {
        modules(module)
    }

    routing {
        get("/") {
            call.respond(HttpStatusCode.OK, "")
        }
    }
}
