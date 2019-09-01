package example.fixtures

import com.ninja_squad.dbsetup_kotlin.DbSetupBuilder
import com.ninja_squad.dbsetup_kotlin.mappedValues

data class MusicsFixture (
    val id: Int = 0, // id
    val artist_id: Int = 0, // アーティストID
    val name: String = "" // 名前
)

fun DbSetupBuilder.insertMusicsFixture(f: MusicsFixture) {
    insertInto("musics") {
        mappedValues(
                "id" to f.id,
                "artist_id" to f.artist_id,
                "name" to f.name
        )
    }
}