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

            // koin.properties の設定値を System.getEnv で設定された値で上書きする
            environmentProperties()

            // koin.properties を読み込んでから getProperty() を呼び出さないとエラーになる
            // koin.getProperty(key, defaultValue) は non-nullable
            Database.connect(
                url = koin.getProperty("db.url", ""),
                driver = koin.getProperty("db.driver", ""),
                user = koin.getProperty("db.user", ""),
                password = koin.getProperty("db.password", "")
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
        MysqlDataSource().apply {
            setURL(getProperty("db.url"))
            user = getProperty("db.user")
            setPassword(getProperty("db.password"))
        } as DataSource
    }
}

