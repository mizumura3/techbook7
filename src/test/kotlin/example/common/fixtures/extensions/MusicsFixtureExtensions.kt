package example.common.fixtures.extensions

import example.fixtures.MusicsFixture

fun MusicsFixture.skrillex(): MusicsFixture = this.copy(
    id = 1,
    name = "Where Are U Now",
    artist_id = 1
)

fun MusicsFixture.zedd(): MusicsFixture = this.copy(
    id = 2,
    name = "Stay The Night",
    artist_id = 2
)
