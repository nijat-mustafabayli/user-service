package com.demo.usermanagement.repo

import com.demo.usermanagement.TEST_USER_EMAIL
import com.demo.usermanagement.TEST_USER_NAME
import com.demo.usermanagement.TEST_USER_PHONE_NUMBER
import com.demo.usermanagement.TestConfigWithDb
import com.demo.usermanagement.repo.entity.User
import com.demo.usermanagement.web.dto.UserRequest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

class UserRepositoryTest(
    @Autowired var userRepository: UserRepository,
) : TestConfigWithDb() {

    @Test
    fun `save should save user`() {
        val saved = userRepository.save(buildUserData())

        assertThat(saved).isNotNull
        assertThat(saved).extracting { u -> u.id }.isNotNull
        assertThat(saved).extracting { u -> u.email }.isEqualTo(TEST_USER_EMAIL)
        assertThat(saved).extracting { u -> u.createdDate }.isNotNull
        assertThat(saved).extracting { u -> u.modifiedDate }.isNotNull
    }

    @Test
    fun `existsByEmail should return true`() {
        val saved = userRepository.save(buildUserData())

        val existsByEmail = userRepository.existsByEmail(saved.email)

        assertThat(existsByEmail).isTrue
    }

}


fun buildUserData() = User(
    id = UUID.randomUUID(),
    name = TEST_USER_NAME,
    email = TEST_USER_EMAIL,
    phoneNumber = TEST_USER_PHONE_NUMBER,
)

fun User.toUserRequest() = UserRequest(
    name = name,
    email = email,
    phoneNumber = phoneNumber,
)