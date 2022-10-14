package com.demo.usermanagement.exceptions

import com.demo.usermanagement.enumeration.ErrorType
import org.springframework.http.HttpStatus

class UserAlreadyExistsException(email: String) : ApplicationException(
    status = HttpStatus.CONFLICT,
    reason = "User with email $email already exists",
    errorType = ErrorType.USER_ALREADY_EXISTS
)