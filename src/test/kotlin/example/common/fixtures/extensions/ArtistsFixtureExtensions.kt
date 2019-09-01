package example.common.fixtures.extensions

import example.fixtures.ArtistsFixture
import java.time.LocalDate

fun ArtistsFixture.skrillex(): ArtistsFixture = this.copy(
    id = 1,
    name = "Skrillex",
    birth = LocalDate.of(1988, 1, 15),
    website = "https://skrillex.com/"
)

fun ArtistsFixture.zedd(): ArtistsFixture = this.copy(
    id = 2,
    name = " ZEDD",
    birth = LocalDate.of(1989, 9, 2),
    website = "https://zedd.net/"
)