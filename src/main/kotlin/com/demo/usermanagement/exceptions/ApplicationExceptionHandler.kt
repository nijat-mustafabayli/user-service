package com.demo.usermanagement.exceptions

import com.demo.usermanagement.enumeration.ErrorType
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.time.OffsetDateTime

@ControllerAdvice
class ApplicationExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(value = [ApplicationException::class])
    fun handle(exception: ApplicationException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(exception.errorType, exception.reason)
        return ResponseEntity(errorResponse, exception.status)
    }

    override fun handleMethodArgumentNotValid(
        exception: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        val errorResponse = ErrorResponse(
            errorType = ErrorType.VALIDATION_ERROR,
            message = "Validation failed for ${exception.bindingResult.objectName}",
            errors = exception.bindingResult.fieldErrors.map {
                ValidationError(it.field, it.defaultMessage)
            }
        )
        return ResponseEntity(errorResponse, status)
    }

    @ExceptionHandler(value = [Exception::class])
    fun handleUnknown(exception: Exception): ResponseEntity<ErrorResponse> {
        logger.error("Unhandled exception was thrown. Please process.", exception)
        val errorResponse = ErrorResponse(
            errorType = ErrorType.UNKNOWN,
            message = "Oops... Something went wrong.",
        )
        return ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    data class ErrorResponse(
        val errorType: ErrorType,
        val message: String,
        val errors: List<ValidationError> = emptyList(),
        val timestamp: OffsetDateTime = OffsetDateTime.now(),
    )

    data class ValidationError(
        val field: String,
        val error: String?,
    )

}
