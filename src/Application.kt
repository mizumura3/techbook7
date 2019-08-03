package com.example

import com.example.repository.ArtistRepository
import com.example.repository.impl.ArtistRepositoryImpl
import com.example.service.ArtistService
import io.ktor.application.*
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.jackson.jackson
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.get
import io.ktor.routing.routing
import org.koin.dsl.module
import org.koin.ktor.ext.Koin
import org.koin.ktor.ext.inject

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

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

    val hogeModule = module {
        single<ArtistRepository> { ArtistRepositoryImpl() }
        single { ArtistService(get()) }
    }

    install(Koin) {
        modules(hogeModule)
    }

    val service: ArtistService by inject()

    routing {
        get("/") {
            call.respond(HttpStatusCode.OK, service.get(1))
        }
    }
}

