package com.turgayozdemir.foodchat.classic

import com.google.ai.client.generativeai.GenerativeModel
import com.turgayozdemir.foodchat.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object ChatData {
    private const val API_KEY = BuildConfig.apiKey
    suspend fun getResponse(prompt: String): Chat {
        val generativeModel = GenerativeModel(
            modelName = "gemini-pro",
            apiKey = API_KEY,
        )

        return try {
            val response = withContext(Dispatchers.IO) {
                generativeModel.generateContent(prompt)
            }

            Chat(
                prompt = response.text ?: "error",
                isFromUser = false,
            )
        } catch (e: Exception) {
            Chat(
                prompt = e.message ?: "error",
                isFromUser = false,
            )
        }
    }
}