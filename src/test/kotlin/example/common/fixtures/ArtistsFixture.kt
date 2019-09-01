package example.fixtures

import java.time.LocalDate
import com.ninja_squad.dbsetup_kotlin.DbSetupBuilder
import com.ninja_squad.dbsetup_kotlin.mappedValues

data class ArtistsFixture (
    val id: Int = 0, // id
    val name: String = "", // 名前
    val birth: LocalDate = LocalDate.now(), // 生年月日
    val website: String? = null // ウェブサイト
)

fun DbSetupBuilder.insertArtistsFixture(f: ArtistsFixture) {
    insertInto("artists") {
        mappedValues(
                "id" to f.id,
                "name" to f.name,
                "birth" to f.birth,
                "website" to f.website
        )
    }
}