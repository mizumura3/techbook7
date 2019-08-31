package example.domain

import org.joda.time.LocalDate


data class Artist(
    val id: Int? = null,
    val name: String,
    val birth: LocalDate,
    val website: String
)