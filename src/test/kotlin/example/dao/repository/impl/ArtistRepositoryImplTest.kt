package example.dao.repository.impl

import example.domain.Artist
import example.repository.ArtistRepository
import com.ninja_squad.dbsetup_kotlin.dbSetup
import example.common.TestBase
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.LocalDate
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.koin.test.inject
import javax.sql.DataSource

internal class ArtistRepositoryImplTest : TestBase() {

    private val repository by inject<ArtistRepository>()
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
    fun findAll() {
        transaction {
            repository.create(
                Artist(
                    name = "Skrillex",
                    birth = LocalDate.parse("2019-01-18"),
                    website = "https://skrillex.com/"
                )
            )

            repository.create(
                Artist(
                    name = "ZEDD",
                    birth = LocalDate.parse("2019-01-18"),
                    website = "https://www.zedd.net/"
                )
            )

            val result = repository.findAll()
            assertThat(result).isNotEmpty()
        }
    }

    /**
     * ID と一致する artist が取得できること
     */
    @Test
    fun findById() {
        transaction {
            val result = repository.findById(1)
            assertThat(result.name).isEqualTo("Skrillex")
        }
    }

    /**
     * artist が insert されること
     */
    @Test
    fun create() {
        transaction {
            val a = Artist(
                name = "Skrillex",
                birth = LocalDate.parse("2019-01-18"),
                website = "skrillex.com"
            )
            val result = repository.create(a)
            assertThat(result).isNotNull()
        }
    }
}