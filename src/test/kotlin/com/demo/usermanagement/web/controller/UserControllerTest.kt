package com.demo.usermanagement.web.controller

import com.demo.usermanagement.TestConfigWithDb
import com.demo.usermanagement.repo.UserRepository
import com.demo.usermanagement.repo.buildUserData
import com.demo.usermanagement.repo.toUserRequest
import com.demo.usermanagement.service.UserService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

internal class UserControllerTest(
    @Autowired private var mockMvc: MockMvc,
    @Autowired private var objectMapper: ObjectMapper,
    @Autowired private var userService: UserService,
    @Autowired private var userRepo: UserRepository,
) : TestConfigWithDb() {

    @BeforeEach
    fun setUp() {
        userRepo.deleteAll()
    }

    @Test
    fun `post users should create user`() {
        val request = buildUserData().toUserRequest()
        mockMvc.perform(
            MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().is2xxSuccessful)
            .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(request.email))
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty)

        assertTrue(userService.isUserExist(request.email))
    }

    @Test
    fun `post users should return bad request by already existed user`() {

        val request = buildUserData().toUserRequest()
        userService.createUser(request)

        mockMvc.perform(
            MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isConflict)
    }
}
