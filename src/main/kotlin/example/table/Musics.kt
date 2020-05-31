package example.table

import org.jetbrains.exposed.dao.id.IntIdTable

/**
 * musics table
 */
object Musics : IntIdTable(name = "musics") {
    val artistId = integer("artist_id")
    val name = varchar("name", 50)
}
