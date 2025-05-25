package com.kotlinprc.accountapi.model.enums

enum class ErrorCode(val description: String) {
    USER_NOT_FOUND("사용자가 없습니다."),
    ACCOUNT_NOT_FOUND("계좌가 없습니다."),
    USER_ACCOUNT_UN_MATCH("사용자와 계좌의 소유주가 다릅니다."),
    ACCOUNT_ALREADY_UNREGISTERED("계좌가 이미 혜지되었습니다."),
    BALANCE_NOT_EMPTY("잔액이 있는 계좌는 혜지 할 수 없습니다."),
    AMOUNT_EXCEED_BALANCE("거래 금액이 계좌 잔액 보다 큽니다")
}