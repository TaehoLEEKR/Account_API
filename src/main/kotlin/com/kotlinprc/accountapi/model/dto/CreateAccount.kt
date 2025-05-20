package com.kotlinprc.accountapi.model.dto

import lombok.Builder
import java.time.LocalDateTime

class CreateAccount {
    data class Request(
        val userId: Long,
        val iniBalance: Long
    )

    @Builder
    data class Response(
        val userId: Long,
        val accountNumber: String,
        val registerAt: LocalDateTime
    )
}
