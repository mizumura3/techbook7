package example.service

import example.domain.Artist
import example.repository.ArtistRepository
import org.jetbrains.exposed.sql.transactions.transaction

class ArtistService(private val artistRepository: ArtistRepository) {

    /**
     * 全件取得する
     *
     * @return Artists 全件
     */
    fun all(): List<Artist> = transaction { artistRepository.findAll() }

    /**
     * Artists を登録する
     *
     * @param artist
     * @return id
     */
    fun create(artist: Artist): Int = transaction { artistRepository.create(artist) }

    /**
     * Artists を取得する
     *
     * @param artistId
     * @return Artist
     */
    fun get(artistId: Int): Artist = transaction { artistRepository.findById(artistId) }
}