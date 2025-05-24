package com.kotlinprc.accountapi.controller

import com.kotlinprc.accountapi.component.StaticLogger.Companion.logger
import com.kotlinprc.accountapi.exception.AccountException
import com.kotlinprc.accountapi.model.dto.AccountDto
import com.kotlinprc.accountapi.model.dto.AccountInfo
import com.kotlinprc.accountapi.model.dto.CreateAccount
import com.kotlinprc.accountapi.model.dto.DeleteAccount
import com.kotlinprc.accountapi.model.enums.ErrorCode
import com.kotlinprc.accountapi.service.AccountService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
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

    @GetMapping("/list")
    fun getAccountUserID(@RequestParam("user_id") userId: Long): List<AccountInfo> {

        var resultList : List<AccountInfo> = accountService.getAccountByUserId(userId);

        if(resultList.isEmpty()) {
            throw AccountException(ErrorCode.ACCOUNT_NOT_FOUND);
        }else{
            return resultList;
        }

    }
}