package example.service

import com.ninja_squad.dbsetup_kotlin.dbSetup
import example.common.TestBase
import example.common.artist_skrillex
import example.common.artist_zedd
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.koin.test.inject
import javax.sql.DataSource

internal class ArtistServiceTest : TestBase() {

    private val service by inject<ArtistService>()
    private val datasource by inject<DataSource>()

    @BeforeEach
    fun before() {
        dbSetup(to = datasource) {
            deleteAllFrom("artists")
        }.launch()
    }

    @DisplayName("artists を全件取得できること")
    @Test
    fun all() {
        // setup
        service.create(artist_skrillex())
        service.create(artist_zedd())

        // exercise
        val result = service.all()

        // verify
        assertThat(result.size).isEqualTo(2)
        assertThat(result[0]).isEqualToIgnoringGivenFields(artist_skrillex(), "id")
        assertThat(result[1]).isEqualToIgnoringGivenFields(artist_zedd(), "id")
    }

    @DisplayName("artists テーブルにレコードが登録されること")
    @Test
    fun create() {
        // exercise
        val result = service.create(artist_skrillex())

        // verify
        assertThat(result).isNotNull()
    }
}