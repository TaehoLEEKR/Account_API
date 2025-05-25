package com.kotlinprc.accountapi.model.dto

import com.kotlinprc.accountapi.model.entity.Account
import com.kotlinprc.accountapi.model.enums.TransactionResult
import com.kotlinprc.accountapi.model.enums.TransactionType
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.ManyToOne
import java.time.LocalDateTime

class TransactionDto(

    var accountNumber : String = "",
    var amount : Long = 0L,
    var transactionType: TransactionType = TransactionType.NOT_USE,

    var transactionResult: TransactionResult = TransactionResult.F,

    var balanceSnapshot : Long = 0L,

    var transactionId : Long = 0L,
    var transactionAt : LocalDateTime = LocalDateTime.now(),

    ) {
}