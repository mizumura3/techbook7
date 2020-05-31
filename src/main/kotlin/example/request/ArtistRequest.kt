package example.request

import java.time.LocalDate


data class ArtistRequest(
    val name: String,
    val birth: LocalDate,
    val website: String
)
