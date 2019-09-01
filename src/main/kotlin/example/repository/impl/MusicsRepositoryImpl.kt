package example.repository.impl

import example.domain.Artist
import example.domain.Music
import example.exception.RecordNotFoundException
import example.repository.MusicsRepository
import example.table.Artists
import example.table.Musics
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll

class MusicsRepositoryImpl : MusicsRepository {

    override fun findById(id: Int): Music {
        return Musics.join(
            Artists,
            JoinType.INNER,
            additionalConstraint = { Artists.id eq Musics.artistId })
            .select { Musics.id eq id }
            .map {
                Music(
                    id = it[Musics.id].value,
                    artist = toArtist(it),
                    name = it[Musics.name]
                )
            }.firstOrNull() ?: throw RecordNotFoundException("Musics record not found. id = $id")
    }

    override fun findAll(): List<Music> {
        return Musics.join(
            Artists,
            JoinType.INNER,
            additionalConstraint = { Artists.id eq Musics.artistId })
            .selectAll()
            .map {
                Music(
                    id = it[Musics.id].value,
                    artist = toArtist(it),
                    name = it[Musics.name]
                )
            }
    }

    private fun toArtist(r: ResultRow): Artist =
        Artist(
            id = r[Musics.artistId],
            name = r[Artists.name],
            birth = r[Artists.birth].toLocalDate(),
            website = r[Artists.website]
        )

}