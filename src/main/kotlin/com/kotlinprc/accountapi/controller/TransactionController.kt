package com.kotlinprc.accountapi.controller

import com.kotlinprc.accountapi.aop.AccountLock
import com.kotlinprc.accountapi.model.dto.TransactionDto
import com.kotlinprc.accountapi.model.dto.UseBalance
import com.kotlinprc.accountapi.model.dto.cancelBalance
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
    @AccountLock
    fun useBalance(
        @RequestBody @Valid request : UseBalance.Request
    ) : UseBalance.Response {
       val transactionDto : TransactionDto =  transactionService.useBalance(
            request.userId,request.accountNumber,
            request.amount
        )
        return UseBalance.Response.fromAccountDto(transactionDto);
    }

    @PostMapping("/transaction/cancel")
    @AccountLock
    fun cancelBalance(@Valid @RequestBody request: cancelBalance.Request) : cancelBalance.Response {
        val transactionDto : TransactionDto =  transactionService.cancelBalance(
            request.transactionId,request.accountNumber,
            request.amount
        )
        return cancelBalance.Response.fromAccountDto(transactionDto);

    }
}