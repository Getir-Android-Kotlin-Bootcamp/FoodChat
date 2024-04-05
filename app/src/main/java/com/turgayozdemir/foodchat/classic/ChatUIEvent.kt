package com.turgayozdemir.foodchat.classic
sealed class ChatUiEvent {
    data class UpdatePrompt(val newPrompt: String) : ChatUiEvent()

    data class SendPrompt(
        val prompt: String,
    ) : ChatUiEvent()

    data object ShowIndicator : ChatUiEvent()
}