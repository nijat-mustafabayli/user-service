package com.demo.usermanagement.exceptions

import com.demo.usermanagement.enumeration.ErrorType
import org.springframework.http.HttpStatus
import java.util.*

class NotFoundException(message: String, errorType: ErrorType = ErrorType.NOT_FOUND) : ApplicationException(
    status = HttpStatus.NOT_FOUND,
    reason = message,
    errorType = errorType,
) {
    companion object {
        fun noSuchUserWithUserId(userId: UUID) = NotFoundException(
            message = "No such user with user id '${userId}'.",
            errorType = ErrorType.USER_NOT_FOUND,
        )
    }
}
