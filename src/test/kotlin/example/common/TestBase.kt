package example.common

import example.module
import org.jetbrains.exposed.sql.Database
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest

abstract class TestBase : KoinTest {

    @BeforeEach
    fun dbconnect() {
        Database.connect(
            "jdbc:mysql://127.0.0.1:3306/example?useSSL=false&serverTimezone=Asia/Tokyo",
            driver = "com.mysql.cj.jdbc.Driver",
            user = "root",
            password = "password"
        )

        startKoin {
            modules(module)
        }
    }

    @AfterEach
    fun autoClose() {
        stopKoin()
    }
}