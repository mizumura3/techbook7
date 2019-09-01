package example.request

import org.joda.time.LocalDate

data class ArtistRequest(
    val name: String,
    val birth: LocalDate,
    val website: String
)
