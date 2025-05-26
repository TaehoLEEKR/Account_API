package com.kotlinprc.accountapi.exception

import com.kotlinprc.accountapi.model.enums.ErrorCode

class AccountException private constructor(
    val errorMessage: String,
    val errorCode: ErrorCode
) : RuntimeException(errorMessage) {

    constructor(errorCode: ErrorCode) : this(
        errorMessage = errorCode.description,
        errorCode = errorCode
    )
}
