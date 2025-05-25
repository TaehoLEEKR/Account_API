package com.kotlinprc.accountapi.service

import com.kotlinprc.accountapi.exception.AccountException
import com.kotlinprc.accountapi.model.dto.TransactionDto
import com.kotlinprc.accountapi.model.entity.Account
import com.kotlinprc.accountapi.model.entity.AccountUser
import com.kotlinprc.accountapi.model.enums.AccountStatus
import com.kotlinprc.accountapi.model.enums.ErrorCode
import com.kotlinprc.accountapi.repository.AccountRepository
import com.kotlinprc.accountapi.repository.AccountUserRepository
import com.kotlinprc.accountapi.repository.TransactionRepository
import jakarta.transaction.Transactional
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class TransactionService (
    private val transactionRepository : TransactionRepository,
    private val accountRepository: AccountRepository,
    private val accountUserRepository: AccountUserRepository
){

    @Transactional
    fun useBalance(userId: Long, accountNumber: String , amount : Long) : TransactionDto {

        var accountUser : AccountUser = accountUserRepository.findById(userId)
            .orElseThrow {
                AccountException(ErrorCode.USER_NOT_FOUND)
            }

        var account : Account = accountRepository.findByAccountNumber(accountNumber)
            .orElseThrow{
                AccountException(ErrorCode.ACCOUNT_NOT_FOUND)
            }
        validationUseBalance(accountUser, account,amount);
        
        return ;
    }

    private fun validationUseBalance(accountUser: AccountUser, account: Account, amount: Long) {

        // 잘못된 ID 경우
        if(accountUser.id != account.accountUser?.id) {
            throw AccountException(ErrorCode.USER_ACCOUNT_UN_MATCH);
        }
        // 혜지된경우
        if(account.accountStatus != AccountStatus.IN_USE){
            throw AccountException(ErrorCode.ACCOUNT_ALREADY_UNREGISTERED)
        }
        // 거래 금액 보다 큰경우
        if(account.balance < amount){
            throw AccountException(ErrorCode.AMOUNT_EXCEED_BALANCE)
        }
    }
}