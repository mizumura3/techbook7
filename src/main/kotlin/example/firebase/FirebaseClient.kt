package example.firebase

import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.Result
import example.controller.FirebaseIdTokenRequest
import example.jacksonObjectMapper
import java.lang.IllegalStateException

/**
 * Firebase にアクセスするクライアント
 */
class FirebaseClient(
    private val firebaseClientSecretConfig: FirebaseClientSecretConfig
) {

    /**
     * トークンを取得する
     */
    fun getIdToken(customToken: String): FirebaseIdTokenResponse {

        val request = FirebaseIdTokenRequest(
            token = customToken,
            returnSecureToken = true
        )

        val triple =
            "https://identitytoolkit.googleapis.com/v1/accounts:signInWithCustomToken?key=${firebaseClientSecretConfig.apiKey}"
                .httpPost()
                .header("content-type", "application/json")
                .body(jacksonObjectMapper().writeValueAsString(request))
                .response()

        println(triple)

        return when (triple.third) {
            is Result.Success -> {
                jacksonObjectMapper().readValue(triple.second.data, FirebaseIdTokenResponse::class.java)
            }

            is Result.Failure -> {
                throw IllegalStateException(triple.third.component2()?.message.orEmpty())
            }
        }
    }
}

data class FirebaseIdTokenResponse(
    val kind: String,
    val idToken: String,
    val refreshToken: String,
    val expiresIn: Int,
    val isNewUser: Boolean
)
