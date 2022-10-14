package com.demo.usermanagement.exceptions

import com.demo.usermanagement.enumeration.ErrorType
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

open class ApplicationException(
    status: HttpStatus,
    reason: String,
    cause: Throwable?,
    val errorType: ErrorType
) : ResponseStatusException(status, reason, cause) {

    constructor(status: HttpStatus, reason: String, errorType: ErrorType) : this(status, reason, null, errorType)

    override fun getReason(): String = super.getReason() ?: "Error"

}