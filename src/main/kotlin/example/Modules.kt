package example

import example.repository.ArtistsRepository
import example.service.ArtistService
import example.controller.ArtistController
import example.repository.MusicsRepository
import example.repository.impl.ArtistsRepositoryImpl
import example.repository.impl.MusicsRepositoryImpl
import org.koin.dsl.module
import org.koin.experimental.builder.single

/**
 * モジュール定義
 */
val sampleModule = module {
    single<ArtistsRepository> { ArtistsRepositoryImpl() }
    single<MusicsRepository> { MusicsRepositoryImpl() }
    single<ArtistService>()
    single<ArtistController>()
}