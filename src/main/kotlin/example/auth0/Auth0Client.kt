package example.auth0

import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.Result
import example.jacksonObjectMapper
import java.lang.IllegalStateException

/**
 * auth0 にアクセスするクライアント
 */
class Auth0Client(
    private val auth0ClientSecretConfig: Auth0ClientSecretConfig
)
{

    /**
     * auth0 のトークンを取得する
     */
    fun oauthToken(): Auth0OAuthTokenResponse {
        val request = Auth0OAuthTokenRequest(
            client_id = auth0ClientSecretConfig.clientId,
            client_secret = auth0ClientSecretConfig.clientSecret,
            audience = auth0ClientSecretConfig.audience,
            grant_type = "client_credentials"
        )

        val triple = "https://mizumura3.auth0.com/oauth/token"
            .httpPost()
            .header("content-type", "application/json")
            .body(jacksonObjectMapper().writeValueAsString(request))
            .response()

        return when (triple.third) {
            is Result.Success -> {
                jacksonObjectMapper().readValue(triple.second.data, Auth0OAuthTokenResponse::class.java)
            }

            is Result.Failure -> {
                throw IllegalStateException(triple.third.component2()?.message.orEmpty())
            }
        }
    }
}
