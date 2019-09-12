package example.common

import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.datatype.joda.JodaModule
import com.mysql.cj.jdbc.MysqlDataSource
import example.exception.ExceptionResponse
import example.exception.RecordNotFoundException
import example.route.root
import example.sampleModule
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
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
import org.jetbrains.exposed.sql.Database
import org.joda.time.LocalDate
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.ktor.ext.getProperty
import org.koin.test.KoinTest
import javax.sql.DataSource

abstract class TestBase : KoinTest {

    @BeforeEach
    fun beforeEach() {
        startKoin {
            // koin.properties の読み込み
            fileProperties()

            // koin.properties の設定値を System.getEnv で設定された値で上書きする
            environmentProperties()

            // koin.properties を読み込んでから getProperty() を呼び出さないとエラーになる
            // koin.getProperty(key, defaultValue) は non-nullable
            Database.connect(
                url = koin.getProperty("db.url", ""),
                driver = koin.getProperty("db.driver", ""),
                user = koin.getProperty("db.user", ""),
                password = koin.getProperty("db.password", "")
            )
            modules(listOf(sampleModule, testModule))
        }
    }

    @AfterEach
    fun autoClose() {
        stopKoin()
    }
}

/**
 * テスト用のモジュール定義
 */
val testModule = module {
    // dbSetUp で使うデータソース
    single {
        MysqlDataSource().apply {
            setURL(getProperty("db.url"))
            user = getProperty("db.user")
            setPassword(getProperty("db.password"))
        } as DataSource
    }
}

@KtorExperimentalLocationsAPI
fun Application.testModule() {
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

    Database.connect(
        getProperty("db.url", ""),
        driver = getProperty("db.driver", ""),
        user = getProperty("db.user", ""),
        password = getProperty("db.password", "")
    )

    routing {
        root()
    }
}
