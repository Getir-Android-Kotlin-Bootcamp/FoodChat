package com.turgayozdemir.foodchat.classic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ChatVMFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChatVM::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ChatVM() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
