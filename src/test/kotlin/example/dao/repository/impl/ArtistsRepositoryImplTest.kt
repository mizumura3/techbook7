package example.dao.repository.impl

import example.repository.ArtistsRepository
import com.ninja_squad.dbsetup_kotlin.dbSetup
import example.common.TestBase
import example.common.artist_skrillex
import example.common.artist_zedd
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.koin.test.inject
import javax.sql.DataSource

internal class ArtistsRepositoryImplTest : TestBase() {

    private val repository by inject<ArtistsRepository>()
    private val datasource by inject<DataSource>()

    @BeforeEach
    fun before() {
        dbSetup(to = datasource) {
            deleteAllFrom("artists")
        }.launch()
    }

    /**
     * 全件取得できること
     */
    @Test
    fun all() {
        transaction {
            // setup
            repository.create(artist_skrillex())
            repository.create(artist_zedd())

            // exercise
            val result = repository.all()

            // verify
            assertThat(result).hasSize(2)
            assertThat(result[0]).isEqualToIgnoringGivenFields(artist_skrillex(), "id")
            assertThat(result[1]).isEqualToIgnoringGivenFields(artist_zedd(), "id")
        }
    }

    /**
     * ID と一致する artist が取得できること
     */
    @Test
    fun findById() {
        transaction {
            // setup
            val id = repository.create(artist_skrillex())

            // exercise
            val result = repository.findById(id)

            // verify
            assertThat(result).isEqualToIgnoringGivenFields(artist_skrillex(), "id")
        }
    }

    /**
     * artist が insert されること
     */
    @Test
    fun create() {
        transaction {
            // exercise
            val result = repository.create(artist_skrillex())

            // verify
            val artist = repository.findById(result)
            assertThat(result).isNotNull()
            assertThat(artist).isEqualToIgnoringGivenFields(artist_skrillex(), "id")
        }
    }

    @Test
    fun count() {
        transaction {

            // exercise
            repository.create(artist_skrillex())
            repository.create(artist_zedd())

            // verify
            val count = repository.count()
            assertThat(count).isEqualTo(2)
        }
    }
}
