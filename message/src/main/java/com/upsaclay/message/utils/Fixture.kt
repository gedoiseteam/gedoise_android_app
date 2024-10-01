package com.upsaclay.message.utils

import com.upsaclay.common.utils.userFixture
import com.upsaclay.common.utils.userFixture2
import com.upsaclay.message.domain.model.Conversation
import com.upsaclay.message.domain.model.Message
import java.time.LocalDateTime

val messageFixture = Message(
    id = "1",
    senderId = userFixture.id,
    text = "Salut, bien et toi ? Oui bien sûr.",
    date = LocalDateTime.of(2024, 7, 20, 10, 0),
    isRead = true,
    type = "text"
)

val messageFixture2 = Message(
    id = "2",
    senderId = userFixture2.id,
    text = "Salut ça va ? Cela fait longtemps que j'attend de te parler. Pourrait-on se voir ?",
    date = LocalDateTime.now(),
    isRead = false,
    type = "text"
)

val messagesFixture = listOf(
    messageFixture,
    messageFixture.copy(text = "Yes"),
    messageFixture,
    messageFixture2,
    messageFixture2,
    messageFixture2,
    messageFixture2.copy(text = "T'es dispo ?"),
    messageFixture,
    messageFixture2,
    messageFixture,
    messageFixture2,
    messageFixture2,
    messageFixture2
)

val conversationFixture = Conversation(
    id = "1",
    interlocutor = userFixture2,
    messages = messagesFixture
)

val conversationsFixture = listOf(
    conversationFixture,
    conversationFixture.copy(messages = messagesFixture.map { it.copy(date = it.date.minusMinutes(1)) }),
    conversationFixture.copy(messages = messagesFixture.map { it.copy(date = it.date.minusMinutes(20)) }),
    conversationFixture.copy(messages = messagesFixture.map { it.copy(date = it.date.minusHours(1)) }),
    conversationFixture.copy(messages = messagesFixture.map { it.copy(date = it.date.minusHours(2)) }),
    conversationFixture.copy(messages = messagesFixture.map { it.copy(date = it.date.minusDays(1)) }),
    conversationFixture.copy(messages = messagesFixture.map { it.copy(isRead = true, date = it.date.minusDays(2)) }),
    conversationFixture.copy(messages = messagesFixture.map { it.copy(isRead = true, date = it.date.minusWeeks(3)) }),
    conversationFixture.copy(messages = messagesFixture.map { it.copy(isRead = true, date = it.date.minusMonths(1)) })

    )