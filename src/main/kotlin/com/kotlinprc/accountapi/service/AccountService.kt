package com.kotlinprc.accountapi.service

import com.kotlinprc.accountapi.component.StaticLogger.Companion.logger
import com.kotlinprc.accountapi.exception.AccountException
import com.kotlinprc.accountapi.model.dto.AccountDto
import com.kotlinprc.accountapi.model.dto.AccountInfo
import com.kotlinprc.accountapi.model.entity.Account
import com.kotlinprc.accountapi.model.entity.AccountUser
import com.kotlinprc.accountapi.model.enums.AccountStatus
import com.kotlinprc.accountapi.model.enums.ErrorCode
import com.kotlinprc.accountapi.repository.AccountRepository
import com.kotlinprc.accountapi.repository.AccountUserRepository
import jakarta.transaction.Transactional
import lombok.RequiredArgsConstructor
import mu.KotlinLogging
import org.springframework.stereotype.Service
import java.util.Objects


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

        logger.info { "========== register service start ==========" }
        // 사용자 조회
        var accountUser : AccountUser = accountUserRepository.findById(userId)
            .orElseThrow {
                AccountException(ErrorCode.USER_NOT_FOUND)
            }
        logger.info { "========== register accountUser end ==========" }

        // 계좌번호 생성
        val newAccountNum: String = accountRepository.findFirstByOrderByIdDesc()
            ?.let {it -> (Integer.parseInt(it.accountNumber) + 1).toString() }
            ?: "1000000000"

        logger.info { "========== register newAccountNum end ========== : {$newAccountNum} " }

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

        logger.info { "========== register service end ==========" }
        return AccountDto.fromEntity(account);

    }

    @Transactional
    fun deleteAccount(userId: Long, accountNumber: String): AccountDto {
        var accountUser : AccountUser = accountUserRepository.findById(userId)
            .orElseThrow {
                AccountException(ErrorCode.USER_NOT_FOUND)
            }

        var account : Account = accountRepository.findByAccountNumber(accountNumber)
            .orElseThrow {
                AccountException(ErrorCode.USER_NOT_FOUND)
            }
        validateDeleteAccount(accountUser, account);

        account.accountStatus = AccountStatus.UNREGISTERED;
        account.unRegisteredAt = java.time.LocalDateTime.now();

        return AccountDto.fromEntity(account);

    }



    private fun validateDeleteAccount(
        accountUser: AccountUser,
        account: Account
    ) {
        if(!Objects.equals(accountUser.id, account.accountUser?.id)) {
            throw AccountException(ErrorCode.USER_ACCOUNT_UN_MATCH);
        }

        if(account.accountStatus == AccountStatus.UNREGISTERED) {
            throw AccountException(ErrorCode.ACCOUNT_ALREADY_UNREGISTERED)
        }

        if(account.balance > 0) {
            throw AccountException(ErrorCode.BALANCE_NOT_EMPTY)
        }
    }

    fun getAccountByUserId(userId: Long) : List<AccountInfo> {
        var accountUser : AccountUser = accountUserRepository.findById(userId)
            .orElseThrow { AccountException(ErrorCode.USER_NOT_FOUND) }

        var accounts : List<Account> = accountRepository.findByAccountUser(accountUser);

        return accounts.map {
            AccountInfo.fromAccountDto(
                AccountDto.fromEntity(it)
            )
        }
//        return ;
    }
}