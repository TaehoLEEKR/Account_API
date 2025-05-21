package com.kotlinprc.accountapi.service

import com.kotlinprc.accountapi.repository.AccountRepository
import com.kotlinprc.accountapi.repository.AccountUserRepository
import jakarta.transaction.Transactional
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service

@Service
class AccountService(
    private val accountRepository: AccountRepository,
    private val accountUserRepository: AccountUserRepository
) {

    /**
     * 사용자가 있는지 조회
     * 계좌의 번호를 생성하고
     * 계좌를 저장하고, 정보를  return
     */
    @Transactional
    fun registerAccount(userId: Long , initBalance: Long) {
            accountUserRepository.findById(userId).orElseThrow { RuntimeException("User not found") }
    }
}