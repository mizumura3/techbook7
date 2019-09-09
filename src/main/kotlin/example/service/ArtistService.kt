package example.service

import example.domain.Artist
import example.repository.ArtistsRepository
import org.jetbrains.exposed.sql.transactions.transaction

class ArtistService(private val artistsRepository: ArtistsRepository) {

    /**
     * 全件取得する
     *
     * @return Artists 全件
     */
    fun all(): List<Artist> = transaction { artistsRepository.all() }

    /**
     * Artists を登録する
     *
     * @param artist
     * @return id
     */
    fun create(artist: Artist): Int = transaction { artistsRepository.create(artist) }

    /**
     * Artists を取得する
     *
     * @param artistId
     * @return Artist
     */
    fun get(artistId: Int): Artist = transaction { artistsRepository.findById(artistId) }
}