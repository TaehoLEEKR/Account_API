package com.kotlinprc.accountapi.controller

import com.kotlinprc.accountapi.service.RedisTestService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/redis/test")
class RedisTestController(
    private val redisTestService: RedisTestService
) {
    @GetMapping("/lock")
    fun getLock() = redisTestService.getLock()
}