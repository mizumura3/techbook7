package example

import example.auth0.Auth0Client
import example.auth0.Auth0ClientSecretConfig
import example.repository.ArtistsRepository
import example.service.ArtistService
import example.controller.ArtistController
import example.controller.Auth0Controller
import example.controller.FirebaseController
import example.controller.HelloController
import example.firebase.FirebaseClient
import example.firebase.FirebaseClientSecretConfig
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
    single<HelloController>()
    single<Auth0Controller>()

    single {
        Auth0ClientSecretConfig(
            clientId = getProperty("auth0.client_id"),
            clientSecret = getProperty("auth0.client_secret"),
            audience = getProperty("auth0.audience")
        )
    }

    single {
        FirebaseClientSecretConfig(
            apiKey = getProperty("firebase.apiKey")
        )
    }

    single<Auth0Client>()
    single<FirebaseClient>()
    single<FirebaseController>()
}
