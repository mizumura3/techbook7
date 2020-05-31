package example.firebase

import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.ninja_squad.dbsetup_kotlin.dbSetup
import example.common.TestBase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.koin.test.inject
import javax.sql.DataSource

internal class FirebaseClientTest : TestBase() {
    private val firebaseClient by inject<FirebaseClient>()
    private val datasource by inject<DataSource>()

    @BeforeEach
    fun before() {
        dbSetup(to = datasource) {}.launch()
    }

    @DisplayName("id token を取得すること")
    @Test
    fun getIdToken() {
        FirebaseApp.initializeApp()
        // 渡す文字列は uid
        val token = FirebaseAuth.getInstance().createCustomToken("SLhMgCvzE0e2lOywBCq7695SogU2")
        val result = firebaseClient.getIdToken(token)

    }
}
