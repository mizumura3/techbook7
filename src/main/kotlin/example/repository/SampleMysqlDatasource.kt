package example.repository

import com.mysql.cj.jdbc.MysqlDataSource
import javax.sql.DataSource

class SampleMysqlDatasource() : MysqlDataSource() {
    constructor(hoge: String) : this() {
        println(hoge)
        setURL("jdbc:mysql://127.0.0.1:3306/example?useSSL=false&serverTimezone=Asia/Tokyo")
        user = hoge
        password = "password"
    }
}

fun mysql(user: String): DataSource {
    val d = MysqlDataSource()
    d.setURL("jdbc:mysql://127.0.0.1:3306/example?useSSL=false&serverTimezone=Asia/Tokyo")
    d.user = "root"
    d.setPassword("password")
    return d
}