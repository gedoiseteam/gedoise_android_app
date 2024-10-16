package com.upsaclay.message.data.repository

import com.upsaclay.message.data.model.ConversationDTO
import kotlinx.coroutines.flow.Flow

internal interface InternalConversationRepository {
    val conversationsDTO: Flow<List<ConversationDTO>>

    suspend fun listenRemoteConversations(userId: Int)

    suspend fun createConversation(conversationDTO: ConversationDTO)
}