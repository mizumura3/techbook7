package example.repository.impl

import example.domain.Artist
import example.exception.RecordNotFoundException
import example.repository.ArtistRepository
import example.table.Artists
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.select

class ArtistRepositoryImpl : ArtistRepository {
    override fun findById(id: Int): Artist {
        return Artists.select { Artists.id eq id }.map {
                Artist(
                    name = it[Artists.name],
                    birth = it[Artists.birth].toLocalDate(),
                    website = it[Artists.website]
                )
            }.firstOrNull() ?: throw RecordNotFoundException("Artists record not found. id = $id")
    }

    override fun create(artist: Artist): Int {
        return Artists.insertAndGetId {
            it[name] = artist.name
            it[birth] = artist.birth.toDateTimeAtCurrentTime()
        }.value
    }
}
