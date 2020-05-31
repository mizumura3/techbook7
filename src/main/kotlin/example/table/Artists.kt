package example.table

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.`java-time`.date


/**
 * artists table
 */
object Artists : IntIdTable(name = "artists") {
    val name = varchar("name", 50)
    val birth = date("birth")
    val website = varchar("website", 200)
}
