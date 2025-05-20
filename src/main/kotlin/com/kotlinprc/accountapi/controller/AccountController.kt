package com.kotlinprc.accountapi.controller

import com.kotlinprc.accountapi.model.dto.CreateAccount
import com.kotlinprc.accountapi.service.AccountService
import jakarta.validation.Valid
import mu.KotlinLogging
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

private val logger = KotlinLogging.logger {}


@RestController
@RequestMapping("/account")
class AccountController(
    private val accountService: AccountService
) {
    @PostMapping("/register")
    fun registerAccount( @RequestBody @Valid createdAccount: CreateAccount.Request): CreateAccount.Response{
        try {
            accountService.registerAccount(createdAccount.userId,createdAccount.iniBalance);
            return  CreateAccount.Response(
                userId = 1L,
                accountNumber = "123456789",
                registerAt = LocalDateTime.now()
            )

        }catch (e: Exception){
            logger.error { "계좌 생성중 Exception 발생 : {$e}" }
            throw e;
        }
    }
}