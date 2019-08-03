package com.example.repository.impl

import com.example.model.Artist
import com.example.repository.ArtistRepository

class ArtistRepositoryImpl : ArtistRepository {

    override fun findById(id: Int): Artist {
        return Artist("Skrillex")
    }
}