package com.kotlinprc.accountapi.exception

import com.kotlinprc.accountapi.component.StaticLogger
import com.kotlinprc.accountapi.component.StaticLogger.Companion.logger
import com.kotlinprc.accountapi.model.dto.Error.ErrorResponse
import com.kotlinprc.accountapi.model.enums.ErrorCode
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler(

) {

    @ExceptionHandler(AccountException::class)
    fun handleAccountException(ex : AccountException) : ErrorResponse.ErrorResponse {
        logger.error("{${ex.message}} is occurred while trying to get account")

        return ErrorResponse.ErrorResponse(ex.errorCode, ex.errorMessage )
    }

    @ExceptionHandler(Exception::class)
    fun handleException(ex : Exception) : ErrorResponse.ErrorResponse {
        logger.error("{${ex.message}} is occurred while trying to get account")

        return ErrorResponse.ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR,ErrorCode.INTERNAL_SERVER_ERROR.description)
    }
}