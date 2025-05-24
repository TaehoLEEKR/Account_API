package com.kotlinprc.accountapi.component

import lombok.experimental.UtilityClass
import mu.KotlinLogging

@UtilityClass
class StaticLogger {
    companion object {
        val logger = KotlinLogging.logger {}
    }
}