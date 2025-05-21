package com.kotlinprc.accountapi.exception

import com.kotlinprc.accountapi.model.enums.ErrorCode

class AccountException private constructor(
    private val errorMessage: String,
    private val errorCode: ErrorCode
) : RuntimeException(errorMessage) {

    constructor(errorCode: ErrorCode) : this(
        errorMessage = errorCode.description,
        errorCode = errorCode
    )
}
