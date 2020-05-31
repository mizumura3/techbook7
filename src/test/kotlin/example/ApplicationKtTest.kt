package example

import com.ninja_squad.dbsetup.Operations.deleteAllFrom
import com.ninja_squad.dbsetup_kotlin.dbSetup
import example.common.TestBase
import example.common.artist_skrillex
import example.common.fixtures.extensions.skrillex
import example.common.testModule
import example.fixtures.ArtistsFixture
import example.fixtures.insertArtistsFixture
import example.request.ArtistRequest
import io.ktor.application.Application
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.setBody
import io.ktor.server.testing.withTestApplication
import org.eclipse.jetty.http.HttpHeader
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.koin.test.inject
import java.time.LocalDate
import javax.sql.DataSource
import kotlin.test.assertEquals

@KtorExperimentalLocationsAPI
internal class ApplicationKtTest : TestBase() {

    private val datasource by inject<DataSource>()

    @DisplayName("アプリケーションルートにアクセスして http status ok(200) が返却されること")
    @Test
    fun test_root() = withTestApplication(Application::testModule) {
        handleRequest(HttpMethod.Get, "").run {
            assertEquals(HttpStatusCode.OK, response.status())
        }
    }

    @DisplayName("/artist のレスポンスが返却されること")
    @Test
    fun test_artists_get() = withTestApplication(Application::testModule) {
        dbSetup(to = datasource) {
            deleteAllFrom("artists")
            insertArtistsFixture(ArtistsFixture().skrillex())
        }.launch()

        handleRequest(HttpMethod.Get, "/artists").run {
            assertEquals(HttpStatusCode.OK, response.status())
            assertEquals(jacksonObjectMapper().writeValueAsString(listOf(artist_skrillex().copy(id = 1))), response.content)
        }
    }

    @DisplayName("/artist が登録 されること")
    @Test
    fun test_artists_post() = withTestApplication(Application::testModule) {
        dbSetup(to = datasource) {
            deleteAllFrom("artists")
        }.launch()

        handleRequest(HttpMethod.Post, "/artists") {
            addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            setBody(ArtistRequest(
                name = "Skrillex",
                birth = LocalDate.of(1988, 1, 15),
                website = "https://skrillex.com/").toJson()
            )
        }.run {
            assertEquals(HttpStatusCode.Created, response.status())
        }
    }
}
