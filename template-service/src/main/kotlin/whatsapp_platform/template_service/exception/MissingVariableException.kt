package whatsapp_platform.template_service.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
class MissingVariableException(val missing: List<String>) : RuntimeException("Missing variables: ${'$'}{missing.joinToString(",")}")
