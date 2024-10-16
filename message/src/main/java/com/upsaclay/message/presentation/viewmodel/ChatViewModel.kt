package com.upsaclay.message.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upsaclay.common.domain.model.User
import com.upsaclay.common.domain.usecase.GetCurrentUserUseCase
import com.upsaclay.message.domain.model.Conversation
import com.upsaclay.message.domain.model.Message
import com.upsaclay.message.domain.model.MessageType
import com.upsaclay.message.domain.usecase.GetConversationUseCase
import com.upsaclay.message.domain.usecase.SendMessageUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ChatViewModel(
    getCurrentUserUseCase: GetCurrentUserUseCase,
    private val getConversationUseCase: GetConversationUseCase,
    private val sendMessageUseCase: SendMessageUseCase
): ViewModel() {
    private val _conversation = MutableStateFlow<Conversation?>(null)
    val conversation: Flow<Conversation?> = _conversation

    val currentUser: Flow<User> = getCurrentUserUseCase().filterNotNull()
    var messageToSend: String by mutableStateOf("")
        private set

    fun updateMessageToSend(text: String) {
        this.messageToSend = text
    }

    fun sendMessage() {
        viewModelScope.launch {
            val message = Message(
                senderId = currentUser.first().id,
                text = messageToSend,
                type = MessageType.TEXT
            )

            sendMessageUseCase(_conversation.value!!.id, message)
        }
    }

    fun getConversation(interlocutorId: Int) {
        viewModelScope.launch {
            getConversationUseCase(interlocutorId).collect {
                _conversation.value = it
            }
        }
    }
}