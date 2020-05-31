package example.common

import example.domain.Artist
import java.time.LocalDate

fun artist_skrillex(): Artist {
    return Artist(
        name = "Skrillex",
        birth = LocalDate.of(1988, 1, 15),
        website = "https://skrillex.com/"
    )
}

fun artist_zedd(): Artist {
    return Artist(
        name = "ZEDD",
        birth = LocalDate.of(1989, 9, 2),
        website = "https://www.zedd.net/"
    )
}
