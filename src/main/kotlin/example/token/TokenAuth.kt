package example.token

import example.exception.ExceptionResponse
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.auth.Authentication
import io.ktor.auth.AuthenticationFailedCause
import io.ktor.auth.AuthenticationFunction
import io.ktor.auth.AuthenticationPipeline
import io.ktor.auth.AuthenticationProvider
import io.ktor.auth.Credential
import io.ktor.auth.Principal
import io.ktor.auth.UnauthorizedResponse
import io.ktor.auth.parseAuthorizationHeader
import io.ktor.http.auth.HttpAuthHeader
import io.ktor.response.respond

private val TokenAuthKey: Any = "TokenAuth"

fun Authentication.Configuration.token(
    name: String? = null,
    configure: TokenAuthenticationProvider.Configuration.() -> Unit
) {
    val provider = TokenAuthenticationProvider.Configuration(name).apply(configure).build()
    val authenticate = provider.authenticationFunction

    provider.pipeline.intercept(AuthenticationPipeline.RequestAuthentication) { context ->
        val token = provider.authHeader(call)
        if (token == null) {
            context.challenge(TokenAuthKey, AuthenticationFailedCause.NoCredentials) {
                call.respond(UnauthorizedResponse())
                it.complete()
            }
            return@intercept
        }
        val credential = TokenCredential(requireNotNull(token.getBlob()))
        val principal = authenticate(call, credential)

        if (principal != null) {
            context.principal(principal)
        } else {
            context.challenge(TokenAuthKey, AuthenticationFailedCause.InvalidCredentials) {
                call.respond(UnauthorizedResponse())
                it.complete()
            }
        }
    }
    register(provider)
}

private fun HttpAuthHeader.getBlob() = if (this is HttpAuthHeader.Single) blob else null

class TokenAuthenticationProvider internal constructor(config: Configuration) : AuthenticationProvider(config) {

    internal val authHeader: (ApplicationCall) -> HttpAuthHeader? = config.authHeader
    internal val authenticationFunction = config.authenticationFunction

    class Configuration internal constructor(name: String?) : AuthenticationProvider.Configuration(name) {

        internal var authHeader: (ApplicationCall) -> HttpAuthHeader? =
            { call -> call.request.parseAuthorizationHeader() }

        var realm: String = "Ktor Server"

        internal var authenticationFunction: AuthenticationFunction<TokenCredential> = { null }

        fun validate(validate: suspend ApplicationCall.(TokenCredential) -> Principal?) {
            authenticationFunction = validate
        }

        internal fun build() = TokenAuthenticationProvider(this)
    }
}

class TokenCredential(val token: String) : Credential
class TokenPrincipal(val token: String) : Principal
