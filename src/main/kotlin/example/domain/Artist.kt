package example.domain

import java.time.LocalDate


data class Artist(
    val id: Int? = null,
    val name: String,
    val birth: LocalDate,
    val website: String,
    val musics: List<Music> = listOf() // TODO left join の説明で使用する
)
