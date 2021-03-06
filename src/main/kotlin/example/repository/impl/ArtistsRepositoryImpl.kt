package example.repository.impl

import example.domain.Artist
import example.exception.RecordNotFoundException
import example.repository.ArtistsRepository
import example.table.Artists
import example.table.Musics
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import java.time.LocalDate

/**
 * Artists を操作する repository の実装
 */
class ArtistsRepositoryImpl : ArtistsRepository {

    override fun all(): List<Artist> {
        return Artists.selectAll().map {
            Artist(
                id = it[Artists.id].value,
                name = it[Artists.name],
                birth = it[Artists.birth],
                website = it[Artists.website]
            )
        }
    }

    override fun findById(id: Int): Artist {
        return Artists.select { Artists.id eq id }.map {
                Artist(
                    id = it[Artists.id].value,
                    name = it[Artists.name],
                    birth = it[Artists.birth],
                    website = it[Artists.website]
                )
            }.firstOrNull() ?: throw RecordNotFoundException("Artists record not found. id = $id")
    }

    override fun create(artist: Artist): Int {
        return Artists.insertAndGetId {
            it[name] = artist.name
            it[birth] = artist.birth
            it[website] = artist.website
        }.value
    }

    override fun count(): Int {
        return Artists.selectAll().count()
    }
}
