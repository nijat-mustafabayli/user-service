package com.demo.usermanagement.web.dto

import com.demo.usermanagement.repo.entity.User
import io.swagger.v3.oas.annotations.media.Schema
import java.util.*
import javax.validation.constraints.Email

data class UserRequest(
    @field:Schema(description = "User name", example = "John", required = true)
    val name: String,

    @field:Schema(description = "User email ", example = "someone@test.com", required = true)
    @get:[Email]
    val email: String,

    @field:Schema(description = "User phone number", example = "+99 9999 999 9999", required = true)
    val phoneNumber: String,
)

data class UserResponse(
    @field:Schema(description = "User identifier", example = "885b6c70-51a7-44dc-909b-0e83eb10b437", required = true)
    val id: UUID,

    @field:Schema(description = "User name", example = "John", required = true)
    val name: String,

    @field:Schema(description = "User email ", example = "someone@test.com", required = true)
    @get:[Email]
    val email: String,

    @field:Schema(description = "User phone number", example = "+99 9999 999 9999", required = true)
    val phoneNumber: String,

    @field:Schema(description = "User age", example = "0", required = true)
    val age: Int?,
)

fun UserRequest.toUser() = User(
    name = name,
    email = email,
    phoneNumber = phoneNumber,
)

fun User.copy(updateUser: UserRequest) = User(
    id = id,
    name = updateUser.name,
    email = updateUser.email,
    phoneNumber = updateUser.phoneNumber,
    age = age,
)