package example.controller

import com.google.firebase.auth.FirebaseAuth
import example.firebase.FirebaseClient
import example.firebase.FirebaseIdTokenResponse

/**
 * Firebase 関連の Controller
 */
class FirebaseController(
    private val firebaseClient: FirebaseClient
) {

    /**
     * Firebase カスタムトークンと ID トークンを返却する
     */
    fun getToken(uid: String): FirebaseIdTokenResponse {
        val token = FirebaseAuth.getInstance().createCustomToken(uid)
        return firebaseClient.getIdToken(token)
    }
}
