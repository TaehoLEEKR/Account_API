package com.kotlinprc.accountapi.model.entity

import com.kotlinprc.accountapi.exception.AccountException
import com.kotlinprc.accountapi.model.enums.AccountStatus
import com.kotlinprc.accountapi.model.enums.ErrorCode
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity
@EntityListeners(AuditingEntityListener::class)
open class Account(
    @Column(nullable = false)
    open var accountNumber: String = "",

    @Enumerated(EnumType.STRING)
    open var accountStatus: AccountStatus = AccountStatus.IN_USE,
    open var balance: Long = 0,

    open var registeredAt: LocalDateTime = LocalDateTime.now(),
    open var unRegisteredAt: LocalDateTime = LocalDateTime.now(),

    @CreatedDate
    open var createdAt: LocalDateTime = LocalDateTime.now(),
    @LastModifiedDate
    open var updatedAt: LocalDateTime = LocalDateTime.now(),

    @ManyToOne
    open var accountUser: AccountUser? = null
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    open var id: Long? = null


    fun useBalance(amount: Long) {
        if(amount > balance) throw AccountException(ErrorCode.AMOUNT_EXCEED_BALANCE);
        balance -= amount
    }
}