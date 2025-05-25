package com.kotlinprc.accountapi.controller

import com.kotlinprc.accountapi.model.dto.UseBalance
import com.kotlinprc.accountapi.service.TransactionService
import jakarta.validation.Valid
import lombok.RequiredArgsConstructor
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
@RequiredArgsConstructor
class TransactionController (
    private val transactionService: TransactionService
){

    @PostMapping("/transaction/use")
    fun useBalance(
        @RequestBody @Valid request : UseBalance.Request
    ) : UseBalance.Response {
        transactionService.
    }
}