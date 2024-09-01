package com.upsaclay.message.domain.usecase

import java.time.LocalDateTime
import java.time.ZoneId

class ConvertLocalDateTimeUseCase {
    fun toTimestamp(localDateTime: LocalDateTime): Long {
        return localDateTime.toEpochSecond(ZoneId.systemDefault().rules.getOffset(localDateTime))
    }
}