package com.kotlinprc.accountapi.model.dto.Error

import com.kotlinprc.accountapi.model.enums.ErrorCode

class ErrorResponse {
    data class ErrorResponse(val errorCode: ErrorCode, val errorMessage:String)
}