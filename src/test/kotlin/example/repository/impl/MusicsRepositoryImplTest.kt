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
        val result = transaction { repository.all() }

        // verify
        assertThat(result).hasSize(2)
    }

    @DisplayName("名称が Purple Lamborghini に更新されること")
    @Test
    fun update() {

        // setup
        dbSetup(to = datasource) {
            insertArtistsFixture(ArtistsFixture().skrillex())
            insertMusicsFixture(MusicsFixture().skrillex())
        }.launch()

        // exercise
        transaction { repository.update(1, "Purple Lamborghini") }

        // verify
        val result = transaction { repository.findById(1) }
        assertThat(result.name).isEqualTo("Purple Lamborghini")
    }

    @DisplayName("artist_id と一致する musics の件数を返却すること")
    @Test
    fun countByArtistId() {
        // setup
        dbSetup(to = datasource) {
            insertArtistsFixture(ArtistsFixture().skrillex())
            insertMusicsFixture(MusicsFixture().skrillex())
            insertMusicsFixture(MusicsFixture().skrillex().copy(id = 2, name = "Bangarang"))
        }.launch()

        // exercise
        val result = transaction { repository.countByArtistId(1) }

        // verify
        assertThat(result).isEqualTo(2)
    }

    @DisplayName(" music の id 最大値を取得すること")
    @Test
    fun maxId() {
        // setup
        insertTestData()

        // verify
        val result = transaction { repository.maxId() }

        // exercise
        assertThat(result).isEqualTo(2)
    }

    @DisplayName("music の id 最小値を取得すること")
    @Test
    fun minId() {
        // setup
        insertTestData()

        // verify
        val result = transaction { repository.minId() }

        // exercise
        assertThat(result).isEqualTo(1)
    }

    @DisplayName("limit で指定した件数取得できること")
    @Test
    fun limit() {

        // setup
        insertTestData()

        // verify
        val result = transaction { repository.limit(2) }

        // exercise
        assertThat(result.size).isEqualTo(2)
    }

    @DisplayName("alias でサブクエリが実行できること")
    @Test
    fun alias() {

        // setup
        insertTestData()

        // verify
        val result = transaction {
            repository.alias()
        }

        // exercise
        assertThat(result.name).isEqualTo("Stay The Night")

    }

    private fun insertTestData() {
        dbSetup(to = datasource) {
            insertMusicsFixture(MusicsFixture().skrillex())
            insertMusicsFixture(MusicsFixture().zedd())
            insertArtistsFixture(ArtistsFixture().skrillex())
            insertArtistsFixture(ArtistsFixture().zedd())
        }.launch()
    }
}
