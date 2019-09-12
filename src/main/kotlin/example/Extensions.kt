package example

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.joda.JodaModule
import example.common.LocalDateSerializer
import org.joda.time.LocalDate
import org.slf4j.Logger
import org.slf4j.LoggerFactory

inline fun <reified T : Any> T.logger(): Logger = LoggerFactory.getLogger(T::class.java)

fun jacksonObjectMapper(): ObjectMapper = com.fasterxml.jackson.module.kotlin.jacksonObjectMapper().registerModule(
    JodaModule().apply {
        addSerializer(
            LocalDate::class.java,
            LocalDateSerializer()
        )
    }
)

fun Any.toJson(): String = jacksonObjectMapper().writeValueAsString(this)