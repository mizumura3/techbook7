package example

import example.repository.ArtistRepository
import example.service.ArtistService
import example.controller.ArtistController
import example.repository.impl.ArtistRepositoryImpl
import org.koin.dsl.module
import org.koin.experimental.builder.single

/**
 * モジュール定義
 */
val sampleModule = module {
    single<ArtistRepository> { ArtistRepositoryImpl() }
    single<ArtistService>()
    single<ArtistController>()
}