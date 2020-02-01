package example.auth0

/**
 * auth0 の access_token の response を保持する
 */
data class Auth0OAuthTokenResponse(
    val access_token: String,
    val token_type: String,
    val expires_in: Int
)
