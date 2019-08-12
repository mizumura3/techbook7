package example.repository

import example.domain.Artist

interface ArtistRepository {
    fun findById(id: Int): Artist
    fun create(artist: Artist): Int
}