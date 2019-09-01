package example.table

import org.jetbrains.exposed.dao.IntIdTable

object Musics : IntIdTable(name = "musics") {
    val artistId = integer("artist_id")
    val name = varchar("name", 50)
}