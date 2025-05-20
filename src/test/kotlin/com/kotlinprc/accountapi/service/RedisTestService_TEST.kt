package com.kotlinprc.accountapi.service

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.redisson.api.RLock
import org.redisson.api.RedissonClient
import java.util.concurrent.TimeUnit
import kotlin.test.assertEquals

class RedisTestService_TEST {
    private lateinit var redissonClient: RedissonClient
    private lateinit var rLock: RLock
    private lateinit var redisTestService: RedisTestService

    @BeforeEach
    fun setUp() {
        redissonClient = mockk()
        rLock = mockk() 

        every { redissonClient.getLock("sampleLock") } returns rLock

        redisTestService = RedisTestService(redissonClient)
    }

    @Test
    fun `락 획득 성공 테스트`() {
        // given
        every { rLock.tryLock(1, 3, TimeUnit.SECONDS) } returns true

        // when
        val result = redisTestService.getLock()

        // then
        assertEquals("Lock acquired", result)
        verify { rLock.tryLock(1, 3, TimeUnit.SECONDS) }
    }

    @Test
    fun `락 획득 실패 테스트`() {
        // given
        every { rLock.tryLock(1, 3, TimeUnit.SECONDS) } returns false

        // when
        val result = redisTestService.getLock()

        // then
        assertEquals("Lock failed", result)
        verify { rLock.tryLock(1, 3, TimeUnit.SECONDS) }
    }

    @Test
    fun `락 획득 중 예외 발생 테스트`() {
        // given
        every { rLock.tryLock(1, 3, TimeUnit.SECONDS) } throws RuntimeException("테스트 예외")

        // when
        val result = redisTestService.getLock()

        // then
        assertEquals(null, result)
        verify { rLock.tryLock(1, 3, TimeUnit.SECONDS) }
    }
}