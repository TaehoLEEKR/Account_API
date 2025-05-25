package com.kotlinprc.accountapi.config

import org.redisson.Redisson
import org.redisson.api.RedissonClient
import org.redisson.config.Config
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RedisRepositoryConfig (
    @Value("\${spring.data.redis.host:127.0.0.1}")
    val host: String,
    @Value("\${spring.data.redis.port:56379}")
    val port: Int,
){

    @Bean
    fun RedissonClient(): RedissonClient? {
        var config = Config()
        config.useSingleServer().setAddress("redis://$host:$port")
        return Redisson.create(config)
    }
}