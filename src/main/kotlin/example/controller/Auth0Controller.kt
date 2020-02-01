package example.controller

import example.auth0.Auth0Client
import example.auth0.Auth0OAuthTokenResponse

/**
 * auth0 にアクセスするコントローラ
 */
class Auth0Controller(
    private val auth0Client: Auth0Client
) {

    /**
     * auth0 の access_token を取得する
     */
    fun getAccessToken(): Auth0OAuthTokenResponse = auth0Client.oauthToken()
}
