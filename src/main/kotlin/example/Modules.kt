package example

import example.repository.ArtistRepository
import com.example.service.ArtistService
import com.mysql.cj.jdbc.MysqlDataSource
import example.repository.impl.ArtistRepositoryImpl
import org.koin.dsl.module
import javax.sql.DataSource

val module = module {
    single { ArtistRepositoryImpl() as ArtistRepository }
    single { ArtistService(get()) }

    // mysql data source
    val dataSource = MysqlDataSource()
    dataSource.setURL("jdbc:mysql://127.0.0.1:3306/example?useSSL=false&serverTimezone=Asia/Tokyo")
    dataSource.user = "root"
    dataSource.setPassword("password")

    single {
        dataSource as DataSource
    }
}