package com.kotlinprc.accountapi.repository

import com.kotlinprc.accountapi.model.entity.Transaction
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface TransactionRepository : JpaRepository<Transaction, Long> {
    fun findByTransactionId(transactionId: String): Optional<Transaction>
}