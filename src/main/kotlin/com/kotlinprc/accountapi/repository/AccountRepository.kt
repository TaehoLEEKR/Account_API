package com.kotlinprc.accountapi.repository

import com.kotlinprc.accountapi.model.entity.Account
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface AccountRepository : JpaRepository<Account, Long> {
    fun findFirstByOrderByIdDesc(): Account?
    fun findByAccountNumber(accountNumber: String): Optional<Account>
}