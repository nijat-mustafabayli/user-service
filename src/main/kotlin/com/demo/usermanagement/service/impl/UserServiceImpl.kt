package com.demo.usermanagement.service.impl

import com.demo.usermanagement.client.AgifyClient
import com.demo.usermanagement.exceptions.NotFoundException
import com.demo.usermanagement.exceptions.UserAlreadyExistsException
import com.demo.usermanagement.repo.UserRepository
import com.demo.usermanagement.repo.entity.User
import com.demo.usermanagement.service.UserService
import com.demo.usermanagement.web.dto.UserRequest
import com.demo.usermanagement.web.dto.UserResponse
import com.demo.usermanagement.web.dto.copy
import com.demo.usermanagement.web.dto.toUser
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class UserServiceImpl(
    private val userRepo: UserRepository,
    private val agifyApi: AgifyClient,
) : UserService {

    @Override
    @Transactional
    override fun createUser(userRequest: UserRequest): UserResponse {
        if (isUserExist(userRequest.email)) {
            throw UserAlreadyExistsException(userRequest.email)
        }

        logger.info("Create user with email=[${userRequest.email}]")

        return userRepo.save(userRequest.toUser()).toUserResponse()
    }

    @Override
    override fun getUser(userId: UUID): UserResponse {
        logger.info("Get user by id=[${userId}]")

        return userRepo.findById(userId).orElseThrow {
            NotFoundException.noSuchUserWithUserId(userId)
        }.toUserResponse()
    }

    @Override
    override fun getUserList(): List<UserResponse> {
        logger.info("Get user list")

        return userRepo.findAll().map { it.toUserResponse() }
    }

    @Override
    @Transactional
    override fun updateUser(userId: UUID, updateUser: UserRequest): UserResponse {
        val user: User = userRepo.findById(userId).orElseThrow {
            NotFoundException.noSuchUserWithUserId(userId)
        }

        if (user.email != updateUser.email && isUserExist(updateUser.email)) {
            throw UserAlreadyExistsException(updateUser.email)
        }

        logger.info("Update user id=[${userId}] data=[${updateUser}]")

        return userRepo.save(user.copy(updateUser)).toUserResponse()
    }

    @Override
    @Transactional
    override fun deleteUser(userId: UUID) {
        logger.info("Remove user with id=[${userId}]")

        if (!userRepo.existsById(userId))
            throw NotFoundException.noSuchUserWithUserId(userId)

        userRepo.deleteById(userId)
    }

    @Override
    override fun isUserExist(email: String): Boolean {
        return userRepo.existsByEmail(email)
    }


    @Override
    override fun getUserAge(name: String): Int? {
        return agifyApi.getUserData(name)?.age
    }

    fun User.toUserResponse() = UserResponse(
        id = id,
        name = name,
        email = email,
        phoneNumber = phoneNumber,
        age = getUserAge(name)
    )

    companion object {
        private val logger = LoggerFactory.getLogger(UserService::class.java)
    }
}
