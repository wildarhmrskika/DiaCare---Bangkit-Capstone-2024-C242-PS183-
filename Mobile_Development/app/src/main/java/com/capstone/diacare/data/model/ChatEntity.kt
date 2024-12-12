package com.capstone.diacare.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("chat")
data class ChatEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    val id: Int = 0,

    @ColumnInfo("message")
    val message: String,
    @ColumnInfo("timestamp")
    val timeStamp: String,
    @ColumnInfo("message_type")
    val messageType: String

)
