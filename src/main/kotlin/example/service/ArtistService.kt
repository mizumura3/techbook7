package com.example.service

import example.domain.Artist
import example.repository.ArtistRepository

class ArtistService(private val artistRepository: ArtistRepository) {
    fun all(): List<Artist> {
        return listOf()
    }
}