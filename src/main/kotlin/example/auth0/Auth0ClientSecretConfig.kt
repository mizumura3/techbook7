package example.auth0

/**
 * auth0 の認証に必要な秘匿情報を保持する
 */
data class Auth0ClientSecretConfig(
    val clientId: String,
    val clientSecret: String,
    val audience: String
)
