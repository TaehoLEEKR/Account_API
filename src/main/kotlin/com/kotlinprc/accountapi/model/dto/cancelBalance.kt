package com.kotlinprc.accountapi.model.dto

import com.kotlinprc.accountapi.aop.AccountLockIdInterface
import com.kotlinprc.accountapi.model.enums.TransactionResult
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import lombok.Builder
import java.time.LocalDateTime

class cancelBalance {

    data class Request(
        @NotNull @Min(1)
        val transactionId: String,

        @NotBlank @Size(min = 10, max = 100)
        val accountNumber: String,

        @NotNull @Min(100)
        val amount: Long
    ): AccountLockIdInterface {
        override fun getAccountNumber(): String? {
            return accountNumber
        }
    }

    @Builder
    data class Response(
        val accountNumber: String,
        val transactionResultType : TransactionResult,
        val transactionId: String,
        val amount : Long,
        val transactionAt: LocalDateTime
    ){
        companion object {
            fun fromAccountDto(transactionDto: TransactionDto): Response {
                return Response(
                    accountNumber = transactionDto.accountNumber,
                    transactionResultType =transactionDto.transactionResult,
                    transactionId =transactionDto.transactionId,
                    amount = transactionDto.amount,
                    transactionAt = transactionDto.transactionAt
                )
            }
        }
    }
}