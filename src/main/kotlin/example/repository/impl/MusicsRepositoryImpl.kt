package example.repository.impl

import example.domain.Artist
import example.domain.Music
import example.exception.RecordNotFoundException
import example.repository.MusicsRepository
import example.table.Artists
import example.table.Musics
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.alias
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.innerJoin
import org.jetbrains.exposed.sql.max
import org.jetbrains.exposed.sql.min
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update

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

    override fun countByArtistId(artistId: Int): Int {
        return Musics.join(
            Artists,
            JoinType.INNER,
            additionalConstraint = { Artists.id eq Musics.artistId })
            .selectAll()
            .count()
    }


    override fun all(): List<Music> {
        return Musics.join(
            Artists,
            JoinType.INNER,
            additionalConstraint = { Artists.id eq Musics.artistId })
            .selectAll()
            .map {
                Music(
                    id = it[Musics.id].value,
                    artist = null,
                    name = it[Musics.name]
                )
            }
    }

    override fun update(id: Int, name: String) {
        Musics.update({ Musics.id eq id }) {
            it[Musics.name] = name
        }
    }

    override fun maxId(): Int {
        val maxId = Musics.id.max()
        val value = Musics.slice(maxId)
            .selectAll()
            .first()[maxId]
            ?.value
        return requireNotNull(value) // id の最大値は必ず取得できるので requireNotNull を使う
    }

    override fun minId(): Int {
        val minId = Musics.id.min()
        val value = Musics.slice(minId)
            .selectAll()
            .first()[minId]
            ?.value
        return requireNotNull(value)
    }

    override fun limit(limit: Int): List<Music> {
        return Musics.join(
            Artists,
            JoinType.INNER,
            additionalConstraint = { Artists.id eq Musics.artistId })
            .selectAll()
            .limit(limit)
            .map {
                Music(
                    id = it[Musics.id].value,
                    artist = toArtist(it),
                    name = it[Musics.name]
                )
            }
    }

    override fun alias(): Music {
        // サブクエリで id が最大の music を取得する
        val maxId = Musics.id.max().alias("id")
        val sub = Musics.slice(maxId).selectAll().alias("sub")
        return Musics.innerJoin(
            sub, { Musics.id }, { sub[Musics.id] }
        ).selectAll().first().let {
            Music(
                id = it[Musics.id].value,
                artist = null,
                name = it[Musics.name]
            )
        }
    }

    override fun batchInsert(artistId: Int, musicNames: List<String>) {
        Musics.batchInsert(musicNames) { name ->
            this[Musics.name] = name
            this[Musics.artistId] = artistId
        }
    }

    private fun toArtist(r: ResultRow): Artist =
        Artist(
            id = r[Musics.artistId],
            name = r[Artists.name],
            birth = r[Artists.birth],
            website = r[Artists.website]
        )
}
