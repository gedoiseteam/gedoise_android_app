package com.upsaclay.message.domain.model

enum class ChatState {
    DEFAULT,
    LOADING,
    MESSAGE_SENT,
    EMPTY_MESSAGE_ERROR,
    SENT_MESSAGE_ERROR
}