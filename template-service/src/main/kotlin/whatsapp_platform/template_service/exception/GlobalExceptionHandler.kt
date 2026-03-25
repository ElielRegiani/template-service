package whatsapp_platform.template_service.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

data class ErrorResponse(val error: String, val message: String, val details: Any? = null)

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFound(ex: NotFoundException): ResponseEntity<ErrorResponse> {
        val body = ErrorResponse("not_found", ex.message ?: "not found", null)
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body)
    }

    @ExceptionHandler(MissingVariableException::class)
    fun handleMissingVariable(ex: MissingVariableException): ResponseEntity<ErrorResponse> {
        val body = ErrorResponse("missing_variable", ex.message ?: "missing variable", mapOf("missing" to ex.missing))
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body)
    }

    @ExceptionHandler(Exception::class)
    fun handleUnknown(ex: Exception): ResponseEntity<ErrorResponse> {
        val body = ErrorResponse("internal_error", ex.message ?: "error")
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body)
    }
}
