package example.service

import example.domain.Artist
import example.repository.ArtistRepository
import org.jetbrains.exposed.sql.transactions.transaction
import javax.sql.DataSource

class ArtistService(private val artistRepository: ArtistRepository) {

    /**
     * 全件取得する
     *
     * @return アーティスト全件
     */
    fun all(): List<Artist> = transaction { artistRepository.findAll() }

    /**
     * アーティストを登録する
     *
     * @param artist
     * @return id
     */
    fun create(artist: Artist): Int = transaction { artistRepository.create(artist) }

}