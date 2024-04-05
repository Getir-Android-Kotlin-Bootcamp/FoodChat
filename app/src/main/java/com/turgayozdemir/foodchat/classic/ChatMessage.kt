package com.turgayozdemir.foodchat.classic

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ChatMessage(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val message: String,
    val isUserMessage: Boolean,
    val timestamp: Long = System.currentTimeMillis(),
    val isStatic: Boolean = false
)