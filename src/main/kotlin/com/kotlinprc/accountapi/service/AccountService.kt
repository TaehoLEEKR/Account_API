package com.kotlinprc.accountapi.service

import com.kotlinprc.accountapi.exception.AccountException
import com.kotlinprc.accountapi.model.dto.AccountDto
import com.kotlinprc.accountapi.model.entity.Account
import com.kotlinprc.accountapi.model.entity.AccountUser
import com.kotlinprc.accountapi.model.enums.AccountStatus
import com.kotlinprc.accountapi.model.enums.ErrorCode
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
    fun registerAccount(userId: Long , initBalance: Long) : AccountDto {

        // 사용자 조회
        var accountUser : AccountUser = accountUserRepository.findById(userId)
            .orElseThrow {
                AccountException(ErrorCode.USER_NOT_FOUND)
            }

        // 계좌번호 생성
        val newAccountNum: String = accountRepository.findFirstByOrderByIdDesc()
            ?.let {it -> (Integer.parseInt(it.accountNumber) + 1).toString() }
            ?: "1000000000"

        // 빌더
        val account = accountRepository.save (
            Account(
                accountNumber = newAccountNum,
                balance = initBalance,
                accountUser = accountUser,
                accountStatus = AccountStatus.IN_USE,
                registeredAt = java.time.LocalDateTime.now()
            )
        )

        return AccountDto.fromEntity(account);

    }
}