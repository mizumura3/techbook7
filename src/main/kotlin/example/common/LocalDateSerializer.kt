package example.common

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import org.joda.time.LocalDate

class LocalDateSerializer : JsonSerializer<LocalDate>() {
    override fun serialize(value: LocalDate?, gen: JsonGenerator?, serializers: SerializerProvider?) {
        gen?.writeString(value?.toString("yyyy-MM-dd"))
    }
}