package example.exception

import java.lang.RuntimeException

class RecordNotFoundException(reason: String) : RuntimeException(reason)
