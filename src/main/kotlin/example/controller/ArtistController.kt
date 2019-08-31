package example.controller

import example.service.ArtistService
import example.domain.Artist

class ArtistController(private val artistService: ArtistService) {
    fun all(): List<Artist> {
        return artistService.all()
    }
}