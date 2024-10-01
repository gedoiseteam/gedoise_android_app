package com.upsaclay.message.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.upsaclay.message.data.model.MESSAGES_TABLE_NAME
import com.upsaclay.message.data.remote.MessageField

@Entity(tableName = MESSAGES_TABLE_NAME)
data class LocalMessage(
    @PrimaryKey
    @ColumnInfo(name = MessageField.MESSAGE_ID)
    val messageId: String,
    @ColumnInfo(name = MessageField.SENDER_ID)
    val senderId: Int,
    @ColumnInfo(name = MessageField.CONVERSATION_ID)
    val conversationId: String,
    @ColumnInfo(name = MessageField.TEXT)
    val text: String,
    @ColumnInfo(name = MessageField.TIMESTAMP)
    val timestamp: Long,
    @ColumnInfo(name = MessageField.IS_READ)
    val isRead: Boolean,
    @ColumnInfo(name = MessageField.TYPE)
    val type: String
)
