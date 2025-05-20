package com.kotlinprc.accountapi.service

import com.kotlinprc.accountapi.repository.AccountRepository
import jakarta.transaction.Transactional
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service

@Service
class AccountService(
    private val accountRepository: AccountRepository
) {

    @Transactional
    fun registerAccount(userId: Long , initBalance: Long) {

    }
}