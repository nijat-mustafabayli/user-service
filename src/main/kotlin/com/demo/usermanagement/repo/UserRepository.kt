package com.demo.usermanagement.repo

import com.demo.usermanagement.repo.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<User, UUID> {
    fun existsByEmail(email: String): Boolean
}