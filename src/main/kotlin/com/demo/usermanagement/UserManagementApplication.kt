package com.demo.usermanagement

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@EnableFeignClients
@SpringBootApplication
@ConfigurationPropertiesScan("com.demo.usermanagement")
class UserManagementApplication

fun main(args: Array<String>) {
    runApplication<UserManagementApplication>(*args)
}
