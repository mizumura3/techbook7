package com.example.repository

import com.example.model.Artist

interface ArtistRepository {
    fun findById(id: Int): Artist
}