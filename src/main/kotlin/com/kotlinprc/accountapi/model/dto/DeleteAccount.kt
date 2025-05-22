package com.kotlinprc.accountapi.model.dto

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import lombok.Builder
import java.time.LocalDateTime

class DeleteAccount {
    data class Request(
        @NotNull @Min(1)
        val userId: Long,
        @NotBlank @Size(min = 10, max = 100)
        val accountNumber: String
    )

    @Builder
    data class Response(
        val userId: Long,
        val accountNumber: String,
        val unRegisterAt: LocalDateTime
    ){
        companion object {
            fun fromAccountDto(accountDto: AccountDto): Response {
                return Response(
                    userId = accountDto.userId,
                    accountNumber = accountDto.accountNumber,
                    unRegisterAt = accountDto.unRegisteredAt
                )
            }
        }
    }
}
