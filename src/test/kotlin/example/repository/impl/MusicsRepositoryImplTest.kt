package example.repository.impl

import com.ninja_squad.dbsetup_kotlin.dbSetup
import example.common.TestBase
import example.common.artist_skrillex
import example.common.fixtures.extensions.skrillex
import example.common.fixtures.extensions.zedd
import example.fixtures.ArtistsFixture
import example.fixtures.MusicsFixture
import example.fixtures.insertArtistsFixture
import example.fixtures.insertMusicsFixture
import example.repository.MusicsRepository
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.koin.test.inject
import javax.sql.DataSource


internal class MusicsRepositoryImplTest : TestBase() {

    private val datasource by inject<DataSource>()
    private val repository by inject<MusicsRepository>()

    @BeforeEach
    fun before() {
        dbSetup(to = datasource) {
            deleteAllFrom("artists", "musics")
        }.launch()
    }

    @DisplayName("id と一致する musics が取得できること")
    @Test
    fun findByid() {

        // setup
        dbSetup(to = datasource) {
            insertArtistsFixture(ArtistsFixture().skrillex())
            insertMusicsFixture(MusicsFixture().skrillex())
        }.launch()

        // exercise
        val result = transaction { repository.findById(1) }

        // verify
        assertThat(result.artist).isEqualToIgnoringGivenFields(artist_skrillex(), "id")
    }

    @DisplayName("musics が全て取得できること")
    @Test
    fun findAll() {

        // setup
        dbSetup(to = datasource) {
            insertArtistsFixture(ArtistsFixture().skrillex())
            insertArtistsFixture(ArtistsFixture().zedd())

            insertMusicsFixture(MusicsFixture().skrillex())
            insertMusicsFixture(MusicsFixture().zedd())
        }.launch()

        // exercise
        val result = transaction { repository.findAll() }

        // verify
        assertThat(result).hasSize(2)
    }
}