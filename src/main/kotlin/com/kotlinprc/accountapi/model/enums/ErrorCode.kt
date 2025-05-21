package com.kotlinprc.accountapi.model.enums

enum class ErrorCode(private val description: String) {
    USER_NOT_FOUND("사용자가 없습니다.")
}