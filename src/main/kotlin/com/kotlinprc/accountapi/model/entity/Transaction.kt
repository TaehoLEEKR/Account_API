package com.kotlinprc.accountapi.model.entity

import com.kotlinprc.accountapi.model.enums.TransactionResult
import com.kotlinprc.accountapi.model.enums.TransactionType
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity
@EntityListeners(AuditingEntityListener::class)
open class Transaction(
    @Enumerated(EnumType.STRING)
    open var transactionType: TransactionType = TransactionType.NOT_USE,

    @Enumerated(EnumType.STRING)
    open var transactionResult: TransactionResult = TransactionResult.F,

    @ManyToOne
    open var account: Account? = null,
    open var amount : Long = 0L,
    open var balanceSnapshot : Long = 0L,

    open var transactionId : String = "",
    open var transactionAt : LocalDateTime = LocalDateTime.now(),

    @CreatedDate
    open var createdAt: LocalDateTime = LocalDateTime.now(),

    @LastModifiedDate
    open var updatedAt: LocalDateTime = LocalDateTime.now()

) {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    open var id: Long? = null

}