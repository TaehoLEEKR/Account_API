package com.kotlinprc.accountapi.model.entity

import com.kotlinprc.accountapi.model.enums.AccountStatus
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class Account(
    @Column(nullable = false)
    var accountNumber: String = "",

    @Enumerated(EnumType.STRING)
    var accountStatus: AccountStatus = AccountStatus.IN_USE
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    var id: Long? = null
}