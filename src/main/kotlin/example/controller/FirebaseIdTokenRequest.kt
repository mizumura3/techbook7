package example.controller

/**
 * FIrebase の ID トークンを取得するリクエスト
 */
data class FirebaseIdTokenRequest(

    /** カスタムトークン */
    val token: String,

    /** リフレッシュトークン返却可否 */
    val returnSecureToken: Boolean
)
