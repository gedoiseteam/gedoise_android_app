package com.upsaclay.message.data.remote.api

import com.upsaclay.message.data.remote.model.RemoteConversation
import kotlinx.coroutines.flow.Flow

internal interface ConversationApi {
    fun listenAllConversations(userId: Int): Flow<List<RemoteConversation>>

    suspend fun createConversation(remoteConversation: RemoteConversation): String

    fun updateConversation(remoteConversation: RemoteConversation)
}