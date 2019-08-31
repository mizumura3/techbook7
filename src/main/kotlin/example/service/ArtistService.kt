package example.service

import example.domain.Artist
import example.repository.ArtistRepository
import org.jetbrains.exposed.sql.transactions.transaction
import javax.sql.DataSource

class ArtistService(private val artistRepository: ArtistRepository) {
    fun all(): List<Artist> = transaction { artistRepository.findAll() }
}