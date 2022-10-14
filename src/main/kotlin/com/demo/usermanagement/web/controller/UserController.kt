package com.demo.usermanagement.web.controller

import com.demo.usermanagement.service.UserService
import com.demo.usermanagement.web.dto.UserRequest
import com.demo.usermanagement.web.dto.UserResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/users")
@Tag(name = "Users API", description = "Allows to create,read,update,delete user")
class UserController(
    private val userService: UserService,
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(method = "POST", summary = "Create user")
    fun create(@RequestBody userRequest: UserRequest): UserResponse {
        return userService.createUser(userRequest)
    }

    @GetMapping
    @Operation(method = "GET", summary = "Get all users list")
    fun getUsers(): Iterable<UserResponse> {
        return userService.getUserList()
    }

    @GetMapping("/{userId}")
    @Operation(method = "GET", summary = "Get user details by user id")
    fun getUser(@PathVariable userId: UUID): UserResponse {
        return userService.getUser(userId)
    }


    @PutMapping("/{userId}")
    @Operation(method = "GET", summary = "Update user details")
    fun updateUser(@PathVariable userId: UUID, @RequestBody updateRequest: UserRequest): UserResponse {
        return userService.updateUser(userId, updateRequest)
    }


    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(method = "DELETE", summary = "Delete user by id")
    fun delete(@PathVariable userId: UUID) {
        userService.deleteUser(userId)
    }

}

