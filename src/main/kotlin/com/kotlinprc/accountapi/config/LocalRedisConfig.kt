package com.kotlinprc.accountapi.config

import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import redis.embedded.RedisServer

@Configuration
class LocalRedisConfig(
    @Value("\${spring.redis.port}")
    var port: Int? = 6379,
) {

    val redisServer: RedisServer = RedisServer(port ?: 6379)

    @PostConstruct
    fun startRedis() {
        redisServer.start()
    }

    @PreDestroy
    fun stopRedis() {
        if(redisServer != null) {
            redisServer.stop()
        }
    }
}
