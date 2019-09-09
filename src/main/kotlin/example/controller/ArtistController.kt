package example.controller

import example.service.ArtistService
import example.domain.Artist
import example.request.ArtistRequest

class ArtistController(private val artistService: ArtistService) {

    /**
     * Artists を全件取得する
     *
     * @return Artists
     */
    fun all(): List<Artist> {
        return artistService.all()
    }

    /**
     * Artists を登録する
     *
     * @param artistRequest
     */
    fun create(artistRequest: ArtistRequest) {
        val artist = Artist(
            id = null,
            name = artistRequest.name,
            birth = artistRequest.birth,
            website = artistRequest.website
        )

        artistService.create(artist)
    }

    /**
     * id で Artist を取得する
     *
     * @param artistId
     * @return Artist
     */
    fun getArtistById(artistId: Int): Artist = artistService.get(artistId)
}