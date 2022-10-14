package com.demo.usermanagement.repo.entity

import org.hibernate.Hibernate
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.OffsetDateTime
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table
import javax.validation.constraints.Email


@Entity
@Table(name = "users")
data class User(
    @Id
    val id: UUID = UUID.randomUUID(),

    val name: String,

    @Email
    val email: String,

    val age: Int? = null,

    val phoneNumber: String,

    @CreatedDate
    val createdDate: OffsetDateTime = OffsetDateTime.now(),

    @LastModifiedDate
    val modifiedDate: OffsetDateTime = OffsetDateTime.now(),

    ) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as User
        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    override fun toString(): String {
        return "DataloaderUser(id=$id, name='$name', email='$email', age=$age, phoneNumber='$phoneNumber', createdDate=$createdDate, modifiedDate=$modifiedDate)"
    }
}
