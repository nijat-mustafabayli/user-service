package com.demo.usermanagement

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class TestConfigWithDb {

    companion object {

        @Container
        val container: PostgreSQLContainer<*> = PostgreSQLContainer("postgres:12.1")
            .withDatabaseName("test")
            .withUsername("test")
            .withPassword("test")
    }
}

const val TEST_USER_EMAIL = "test@test.com"
const val TEST_USER_NAME = "test"
const val TEST_USER_PHONE_NUMBER = "99999999"
