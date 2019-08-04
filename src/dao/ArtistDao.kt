package com.example.dao

import com.example.table.Artists
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass

class ArtistDao(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ArtistDao>(Artists)
    var name by Artists.name
}