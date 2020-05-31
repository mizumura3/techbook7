package example.repository

import example.domain.Music

interface MusicsRepository {
    fun findById(id: Int): Music
    fun countByArtistId(artistId: Int): Int
    fun all(): List<Music>
    fun update(id: Int, name: String)
    fun maxId(): Int
    fun minId(): Int
    fun limit(limit: Int): List<Music>
    fun alias(): Music
}
