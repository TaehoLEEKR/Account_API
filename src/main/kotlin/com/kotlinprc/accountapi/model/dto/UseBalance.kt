package com.kotlinprc.accountapi.model.dto

import com.kotlinprc.accountapi.model.enums.TransactionResult
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import lombok.Builder
import java.time.LocalDateTime

class UseBalance {
    data class Request(
        @NotNull @Min(1)
        val userId: Long,

        @NotBlank @Size(min = 10, max = 100)
        val accountNumber: String,

        val amount: Long? = 0
    )

    @Builder
    data class Response(
        val accountNumber: String,
        val transactionResultType : TransactionResult,
        val transactionId: String,
        val amount : Long,
        val transactionAt: LocalDateTime
    )
}