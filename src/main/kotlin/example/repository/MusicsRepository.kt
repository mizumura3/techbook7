package example.repository

import example.domain.Music

interface MusicsRepository {
    fun findById(id: Int): Music
    fun all(): List<Music>
    fun update(id: Int, name: String)
}