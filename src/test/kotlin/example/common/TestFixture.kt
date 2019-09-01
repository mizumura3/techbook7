package example.common

import example.domain.Artist
import org.joda.time.LocalDate

fun artist_skrillex(): Artist {
    return Artist(
        name = "Skrillex",
        birth = LocalDate.parse("1988-01-15"),
        website = "https://skrillex.com/"
    )
}

fun artist_zedd(): Artist {
    return Artist(
        name = "ZEDD",
        birth = LocalDate.parse("1989-09-02"),
        website = "https://www.zedd.net/"
    )
}