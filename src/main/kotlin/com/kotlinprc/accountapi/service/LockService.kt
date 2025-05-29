package com.kotlinprc.accountapi.service

import com.kotlinprc.accountapi.component.StaticLogger.Companion.logger
import com.kotlinprc.accountapi.exception.AccountException
import com.kotlinprc.accountapi.model.enums.ErrorCode
import org.redisson.api.RLock
import org.redisson.api.RedissonClient
import org.springframework.stereotype.Service

@Service
class LockService(
    private val redissonClient: RedissonClient,
) {

    fun getLock(accountNumber : String ){
        val lock: RLock = redissonClient.getLock("${getLockKey()}:"+accountNumber);

        logger.debug { "Lock acquired : {$accountNumber}"  };

        try{
            val isLock : Boolean = lock.tryLock(1, 10, java.util.concurrent.TimeUnit.SECONDS)

            logger.info { "Lock failed : {$isLock}"  };

            if(!isLock){
                throw  AccountException(ErrorCode.LOCK_FAILED);
            }

        }catch (e: AccountException){
            throw e;
        }
        catch (e: Exception){
            logger.error { "Lock failed : {$e}"  };
        }

    }

    fun unLock(accountNumber : String ){
        logger.debug { "Lock released : {$accountNumber}"  };
        redissonClient.getLock("${getLockKey()}:"+accountNumber).unlock();
    }

    private fun getLockKey(): String = "ACLK"
}