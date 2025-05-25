package com.kotlinprc.accountapi.config

import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import redis.embedded.RedisServer
import java.io.IOException
import java.net.ServerSocket

@Configuration
class LocalRedisConfig(
    @Value("\${spring.data.redis.port:56379}")
    var redisPort: Int = 56379,

    @Value("\${spring.data.redis.host:127.0.0.1}")
    var redisHost: String = "127.0.0.1"
) {
    private var redisServer: RedisServer? = null
    private val logger = com.kotlinprc.accountapi.component.StaticLogger.logger

    @PostConstruct
    fun startRedis() {
        try {
            // 포트 사용 가능 여부 확인
            val port = if (isPortAvailable(redisPort)) redisPort else findAvailablePort()

            redisServer = RedisServer.builder()
                .port(port)
                .setting("bind " + redisHost)
                .build()

            redisServer?.start()
            logger.info("임베디드 Redis 서버가 {}:{} 에서 시작되었습니다", redisHost, port)
        } catch (e: Exception) {
            logger.error("Redis 서버 시작 중 오류 발생: {}", e.message, e)
        }
    }

    @PreDestroy
    fun stopRedis() {
        try {
            redisServer?.let {
                if (it.isActive) {
                    it.stop()
                    logger.info("임베디드 Redis 서버가 중지되었습니다")
                }
            }
        } catch (e: Exception) {
            logger.error("Redis 서버 중지 중 오류 발생: {}", e.message)
        }
    }

    private fun isPortAvailable(port: Int): Boolean {
        return try {
            ServerSocket(port).use { true }
        } catch (e: IOException) {
            false
        }
    }

    private fun findAvailablePort(): Int {
        return try {
            ServerSocket(0).use { it.localPort }
        } catch (e: IOException) {
            6380
        }
    }
}