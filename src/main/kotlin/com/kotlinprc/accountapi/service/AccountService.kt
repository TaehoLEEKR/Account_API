package com.kotlinprc.accountapi.service

import com.kotlinprc.accountapi.repository.AccountRepository
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service

@Service
class AccountService(
    private val accountRepository: AccountRepository
) {
}