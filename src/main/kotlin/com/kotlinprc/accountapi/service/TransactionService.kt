package com.kotlinprc.accountapi.service

import com.kotlinprc.accountapi.repository.TransactionRepository
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class TransactionService (
    private val transactionRepository : TransactionRepository
){
}