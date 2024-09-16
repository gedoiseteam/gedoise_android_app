package com.upsaclay.message.domain.model

import com.upsaclay.common.domain.model.User
import java.time.LocalDateTime

data class Message(
    val id: Int,
    val text: String,
    val date: LocalDateTime,
    val sender: User
)