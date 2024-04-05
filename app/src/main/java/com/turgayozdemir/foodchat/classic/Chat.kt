package com.turgayozdemir.foodchat.classic


data class Chat(
    val prompt: String,
    val isFromUser: Boolean,
    val isStatic: Boolean = false
)