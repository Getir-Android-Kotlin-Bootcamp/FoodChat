package com.turgayozdemir.foodchat.classic


data class ChatState(
    val chatList: MutableList<Chat> = mutableListOf(),
    var prompt: String = "",
    val showIndicator: Boolean = false,
)