package com.kotlinprc.accountapi.model.dto

import com.kotlinprc.accountapi.model.entity.Account
import java.time.LocalDateTime

class AccountDto (
    val accountNumber: String,
    val balance: Long,
    val userId : Long,
    var registeredAt: LocalDateTime,
    var unRegisteredAt: LocalDateTime
){
    companion object {
        fun fromEntity(account: Account): AccountDto {
            return AccountDto(
                accountNumber = account.accountNumber,
                balance = account.balance,
                userId = account.accountUser?.id ?:
                    throw IllegalArgumentException("AccountUser가 null이거나 ID가 없습니다"),
                registeredAt = account.registeredAt,
                unRegisteredAt = account.unRegisteredAt
            )
        }
    }

}