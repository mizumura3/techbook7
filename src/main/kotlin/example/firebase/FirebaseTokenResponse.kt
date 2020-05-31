package example.firebase

/**
 * Firebase の ID token レスポンス
 */
data class FirebaseTokenResponse(
    /** カスタムトークン */
    val customToken: String,

    /** ID トークン */
    val idToken: String,

    /** リフレッシュトークン */
    val refreshToken: String
)
