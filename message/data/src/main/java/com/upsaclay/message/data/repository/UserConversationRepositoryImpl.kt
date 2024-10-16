package com.upsaclay.message.data.repository

import com.upsaclay.common.domain.repository.UserRepository
import com.upsaclay.message.data.mapper.ConversationMapper
import com.upsaclay.message.data.mapper.MessageMapper
import com.upsaclay.message.data.model.ConversationDTO
import com.upsaclay.message.domain.model.Conversation
import com.upsaclay.message.domain.model.Message
import com.upsaclay.message.domain.repository.ConversationRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

internal class UserConversationRepositoryImpl(
    private val internalConversationRepository: InternalConversationRepository,
    private val internalMessageRepository: InternalMessageRepository,
    userRepository: UserRepository
) : ConversationRepository {
    private val _conversations = MutableStateFlow<List<Conversation>>(emptyList())
    override val conversations: Flow<List<Conversation>> = _conversations
    private val currentUser = userRepository.currentUser.filterNotNull()
    private var previousConversations = emptyList<ConversationDTO>()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            currentUser.collect {
                internalConversationRepository.listenRemoteConversations(it.id)
            }
        }

        CoroutineScope(Dispatchers.IO).launch {
            internalConversationRepository.conversationsDTO.collect { conversationsDTO ->
                val newsConversationsDTO = conversationsDTO - previousConversations.toSet()

                newsConversationsDTO.forEach { conversationDTO ->
                    launch {
                        internalMessageRepository.listenLastMessages(conversationDTO.conversationId).collect { messagesDTO ->
                            val messages = messagesDTO.map(MessageMapper::toDomain)
                            updateConversationsMessages(conversationDTO.conversationId, messages)
                        }
                    }
                }

                previousConversations = conversationsDTO
            }
        }
    }

    override suspend fun createConversation(conversation: Conversation) {
        val participantsIds = listOf(currentUser.first().id, conversation.interlocutor.id)
        internalConversationRepository.createConversation(ConversationMapper.toDTO(conversation, participantsIds))
    }

    private fun updateConversationsMessages(conversationId: String, messages: List<Message>) {
        _conversations.value = _conversations.value.map { conversation ->
            if (conversation.id == conversationId) {
                val updatedMessages = messages.filterNot { conversation.messages.contains(it) } + conversation.messages
                conversation.copy(messages = updatedMessages)
            } else {
                conversation
            }
        }
    }
}