package com.kotlinprc.accountapi.controller

import com.kotlinprc.accountapi.component.StaticLogger.Companion.logger
import com.kotlinprc.accountapi.model.dto.AccountDto
import com.kotlinprc.accountapi.model.dto.CreateAccount
import com.kotlinprc.accountapi.model.dto.DeleteAccount
import com.kotlinprc.accountapi.service.AccountService
import jakarta.validation.Valid
import mu.KotlinLogging
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime



@RestController
@RequestMapping("/account")
class AccountController(
    private val accountService: AccountService
) {
    @PostMapping("/register")
    fun registerAccount( @RequestBody @Valid createdAccount: CreateAccount.Request): CreateAccount.Response{
        try {
            logger.info("========= register start ========");

            var accountDto : AccountDto = accountService.registerAccount(createdAccount.userId,createdAccount.iniBalance);

            logger.info("========= register end ======== : {$accountDto} ");

            return  CreateAccount.Response.fromAccountDto(accountDto);

        }catch (e: Exception){
            logger.error { "계좌 생성중 Exception 발생 : {$e}" }
            throw e;
        }
    }

    @DeleteMapping("/register")
    fun deleteAccount( @RequestBody @Valid deletedAccount: DeleteAccount.Request): DeleteAccount.Response{
        try {
            logger.info("========= register start ========");

            var accountDto : AccountDto = accountService.deleteAccount(deletedAccount.userId,deletedAccount.accountNumber);

            logger.info("========= register end ======== : {$accountDto} ");

            return DeleteAccount.Response.fromAccountDto(accountDto);

        }catch (e: Exception){
            logger.error { "계좌 생성중 Exception 발생 : {$e}" }
            throw e;
        }
    }
}