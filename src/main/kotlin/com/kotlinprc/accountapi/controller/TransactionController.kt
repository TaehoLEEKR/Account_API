package com.kotlinprc.accountapi.controller

import com.kotlinprc.accountapi.model.dto.UseBalance
import jakarta.validation.Valid
import lombok.RequiredArgsConstructor
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
@RequiredArgsConstructor
class TransactionController {

    @PostMapping("/transaction/use")
    fun useBalance(
        @RequestBody @Valid request : UseBalance.Request
    ) : UseBalance.Request {

    }
}