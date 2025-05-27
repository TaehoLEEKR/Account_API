package com.kotlinprc.accountapi.controller

import com.kotlinprc.accountapi.service.LockService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/redis/test")
class RedisTestController(
    private val lockService: LockService
) {
    @GetMapping("/lock")
    fun getLock() = lockService.getLock("")
}