package com.kotlinprc.accountapi.model.dto

class AccountInfo (
    val accountNumber: String,
    val balance: Long
){
    companion object {
        fun fromAccountDto(accountDto: AccountDto): AccountInfo {
            return AccountInfo(
                accountNumber = accountDto.accountNumber,
                balance = accountDto.balance
            )
        }
    }
}