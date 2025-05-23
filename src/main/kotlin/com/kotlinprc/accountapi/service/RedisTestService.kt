package com.kotlinprc.accountapi.service

import com.kotlinprc.accountapi.component.StaticLogger
import com.kotlinprc.accountapi.component.StaticLogger.Companion.logger
import mu.KotlinLogging
import org.redisson.api.RLock
import org.redisson.api.RedissonClient
import org.springframework.stereotype.Service

@Service
class RedisTestService(
    private val redissonClient: RedissonClient,
) {

    fun getLock(): String?{
        val lock: RLock = redissonClient.getLock("sampleLock")

        try{
            val isLock : Boolean = lock.tryLock(1, 3, java.util.concurrent.TimeUnit.SECONDS)

            logger.info { "Lock failed : {$isLock}"  };

            if(!isLock){
                return "Lock failed"
            }

        }catch (e: Exception){
            logger.error { "Lock failed : {$e}"  };
            return null
        }

        return "Lock acquired"
    }
}