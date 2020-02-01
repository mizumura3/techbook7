package example.auth0

/**
 * auth0 の access_token の response を保持する
 */
data class Auth0OAuthTokenRequest(
    val client_id: String,
    val client_secret: String,
    val audience: String,
    val grant_type: String
)
