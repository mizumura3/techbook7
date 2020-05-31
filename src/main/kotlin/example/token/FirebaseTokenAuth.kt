package example.token

import com.auth0.jwk.GuavaCachedJwkProvider
import com.auth0.jwk.UrlJwkProvider
import io.ktor.auth.Authentication
import io.ktor.auth.AuthenticationContext
import io.ktor.auth.jwt.JWTPrincipal
import io.ktor.auth.jwt.jwt
import java.net.URL

// Googleが公開している JWK_PROVIDER_URL
internal const val JWK_PROVIDER_URL = "https://www.googleapis.com/service_accounts/v1/jwk/securetoken@system.gserviceaccount.com"

class FirebaseTokenAuth(val name:String?) {
    var projectId: String = "" // FirebaseのプロジェクトID
}

fun Authentication.Configuration.firebaseJwt(
    name: String? = null,
    configure: FirebaseTokenAuth.() -> Unit
) {
    val config = FirebaseTokenAuth(name).apply(configure)
    val jwkIssuer = "https://securetoken.google.com/${config.projectId}"
    val audience = config.projectId
    val jwkProvider = UrlJwkProvider(URL(JWK_PROVIDER_URL))
    val cachedJwkProvider = GuavaCachedJwkProvider(jwkProvider)
    jwt(config.name) {
        verifier(cachedJwkProvider, jwkIssuer)
        validate { credentials ->
            if (credentials.payload.audience.contains(audience)) JWTPrincipal(credentials.payload) else null
        }
    }
}

val AuthenticationContext.firebaseUserId: String?
    get() = this.principal<JWTPrincipal>()?.payload?.subject
