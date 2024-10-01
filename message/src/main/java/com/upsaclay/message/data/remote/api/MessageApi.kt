package com.upsaclay.message.data.remote.api

import com.upsaclay.message.data.remote.model.RemoteMessage
import kotlinx.coroutines.flow.Flow

interface MessageApi {
    fun listenLastMessages(conversationId: String): Flow<List<RemoteMessage>>

    suspend fun getMessages(conversationId: String, limit: Long): List<RemoteMessage>
}