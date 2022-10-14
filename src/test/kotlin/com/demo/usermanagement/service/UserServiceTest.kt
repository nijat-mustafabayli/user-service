package com.demo.usermanagement.service

import com.demo.usermanagement.client.AgifyClient
import com.demo.usermanagement.client.AgifyUserData
import com.demo.usermanagement.exceptions.NotFoundException
import com.demo.usermanagement.exceptions.UserAlreadyExistsException
import com.demo.usermanagement.repo.UserRepository
import com.demo.usermanagement.repo.buildUserData
import com.demo.usermanagement.repo.entity.User
import com.demo.usermanagement.repo.toUserRequest
import com.demo.usermanagement.service.impl.UserServiceImpl
import io.mockk.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows
import org.springframework.http.HttpStatus
import java.util.*


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserServiceTest {
    private val userRepo: UserRepository = mockk()

    private val agifyApi: AgifyClient = mockk()

    private val userService = UserServiceImpl(
        userRepo = userRepo,
        agifyApi = agifyApi
    )

    @BeforeEach
    fun setup() {
        clearMocks(userRepo, agifyApi)
        every { userRepo.save(any()) } returnsArgument 0
    }

    @Test
    fun `createUser should save one user`() {

        val userData = buildUserData()

        every { userRepo.existsByEmail(userData.email) } returns false

        every { agifyApi.getUserData(userData.name) } returns AgifyUserData(18, 1, userData.name)

        every { userRepo.save(userData) } returns userData

        userService.createUser(userData.toUserRequest())

        verify(exactly = 1) { userRepo.save(any()) }
    }

    @Test
    fun `createUser should throw exception if email exists`() {

        val userData = buildUserData()

        every { userRepo.existsByEmail(userData.email) } returns true

        val e = assertThrows<UserAlreadyExistsException> {
            userService.createUser(userData.toUserRequest())
        }

        assertThat(e.status).isEqualTo(HttpStatus.CONFLICT)

        verify { userRepo.save(userData) wasNot Called }
    }

    @Test
    fun `getUser should return user by given id`() {

        val expectedUser: User = buildUserData()

        every { userRepo.findById(expectedUser.id) } returns Optional.of(expectedUser)

        every { agifyApi.getUserData(expectedUser.name) } returns AgifyUserData(18, 1, expectedUser.name)

        val actual = userService.getUser(expectedUser.id)

        assertThat(expectedUser.id).isEqualTo(actual.id)

        verify(exactly = 1) { userRepo.findById(expectedUser.id) }
    }

    @Test
    fun `deleteUser should delete user by id`() {

        val userId = UUID.randomUUID()

        every { userRepo.existsById(userId) } returns true

        every { userRepo.deleteById(userId) } returnsArgument 0

        userService.deleteUser(userId)

        verify(exactly = 1) { userRepo.deleteById(any()) }
    }

    @Test
    fun `deleteUser should throw exception if user not found`() {

        val userId = UUID.randomUUID()

        every { userRepo.existsById(userId) } returns false

        val e = assertThrows<NotFoundException> {
            userService.deleteUser(userId)
        }

        assertThat(e.status).isEqualTo(HttpStatus.NOT_FOUND)
    }
}