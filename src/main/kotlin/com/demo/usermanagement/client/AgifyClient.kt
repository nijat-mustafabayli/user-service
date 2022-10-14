package com.demo.usermanagement.client

import org.springframework.cloud.openfeign.FallbackFactory
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam


@FeignClient(
    name = "agifyApi",
    url = "https://api.agify.io",
    fallbackFactory = HystrixClientFallbackFactory::class
)
interface AgifyClient {

    @GetMapping("/")
    fun getUserData(@RequestParam("name") name: String): AgifyUserData?

}

@Component
internal class HystrixClientFallbackFactory : FallbackFactory<AgifyClient> {

    override fun create(cause: Throwable?): AgifyClient {

        return object : AgifyClient {
            override fun getUserData(name: String): AgifyUserData {
                return AgifyUserData(
                    age = null, count = 0, name = name
                )
            }
        }

    }
}

data class AgifyUserData(
    var age: Int? = null, var count: Int? = null, var name: String? = null
)