package com.example

import com.fasterxml.jackson.datatype.joda.JodaModule
import example.common.LocalDateSerializer
import example.sampleModule
import example.route.root
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.jackson.jackson
import io.ktor.locations.Locations
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

    install(Koin) {
        // ktor から設定読み込んで koin の property に設定する方法 今は使わない
        // koin.setProperty("db.user", environment.config.property("db.user").getString())

        // こうやって書くと koin.properties を読んでから System.getEnv で環境変数を読んで格納するので
        // 同じキー名の環境変数があった場合は上書きする
        fileProperties()
        environmentProperties()

        modules(sampleModule)
    }

    routing {
        root()
    }
}
