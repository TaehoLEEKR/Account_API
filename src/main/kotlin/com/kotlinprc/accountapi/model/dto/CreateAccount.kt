package com.kotlinprc.accountapi.model.dto

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import lombok.Builder
import java.time.LocalDateTime

class CreateAccount {
    data class Request(
        @NotNull @Min(1)
        val userId: Long,
        @NotNull @Min(100)
        val iniBalance: Long
    )

    @Builder
    data class Response(
        val userId: Long,
        val accountNumber: String,
        val registerAt: LocalDateTime
    )
}
