package com.example.service

import com.example.model.Artist
import com.example.repository.ArtistRepository

class ArtistService(
    private val artistRepository: ArtistRepository
) {

    fun get(artistId: Int): Artist {
        return artistRepository.findById(artistId)
    }
}