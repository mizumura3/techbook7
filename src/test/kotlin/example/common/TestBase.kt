package example.common

import com.mysql.cj.jdbc.MysqlDataSource
import example.sampleModule
import org.jetbrains.exposed.sql.Database
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import javax.sql.DataSource

abstract class TestBase : KoinTest {

    @BeforeEach
    fun beforeEach() {
        startKoin {
            // koin.properties の読み込み
            fileProperties()

            // koin の環境変数読み込み
            environmentProperties()

            Database.connect(
                url = koin.getProperty("db.url")!!,
                driver = koin.getProperty("db.driver")!!,
                user = koin.getProperty("db.user")!!,
                password = koin.getProperty("db.password")!!
            )
            modules(listOf(sampleModule, testModule))
        }
    }

    @AfterEach
    fun autoClose() {
        stopKoin()
    }
}

/**
 * テスト用のモジュール定義
 */
val testModule = module {
    // dbSetUp で使うデータソース
    single {
        val dataSource = MysqlDataSource()
        dataSource.setURL(getProperty("db.url"))
        dataSource.user = getProperty("db.user")
        dataSource.setPassword(getProperty("db.password"))
        dataSource as DataSource
    }
}

