package com.demo.usermanagement.service

import com.demo.usermanagement.web.dto.UserRequest
import com.demo.usermanagement.web.dto.UserResponse
import java.util.*

interface UserService {
    fun createUser(userRequest: UserRequest): UserResponse
    fun getUser(userId: UUID): UserResponse
    fun getUserList(): Iterable<UserResponse>
    fun updateUser(userId: UUID, updateUser: UserRequest): UserResponse
    fun deleteUser(userId: UUID)
    fun isUserExist(email: String): Boolean
    fun getUserAge(name: String): Int?
}