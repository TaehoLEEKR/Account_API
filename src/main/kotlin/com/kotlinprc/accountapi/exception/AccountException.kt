package com.kotlinprc.accountapi.exception

import com.kotlinprc.accountapi.model.enums.ErrorCode

class AccountException(private val errorMessage: String, private val errorCode : ErrorCode) : RuntimeException(){

}