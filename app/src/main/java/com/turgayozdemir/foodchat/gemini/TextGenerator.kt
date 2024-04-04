package com.turgayozdemir.foodchat.gemini

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.BlockThreshold
import com.google.ai.client.generativeai.type.HarmCategory
import com.google.ai.client.generativeai.type.SafetySetting
import com.google.ai.client.generativeai.type.generationConfig
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

class TextGenerator() {

    private var job : Job? = null

    var handler : CoroutineExceptionHandler? = null

    val API_KEY = "AIzaSyBSPaMai9CLAEGxNwkfp9P4RQGMPm2zPhU"

    suspend fun generateText(prompt: String): String {
        // Bu metodun içerisinde modeli tanımlayıp, mesajı gönderiyoruz.
        val model = GenerativeModel(
            "gemini-1.0-pro",
            API_KEY,
            generationConfig = generationConfig {
                temperature = 0.9f
                topK = 1
                topP = 1f
                maxOutputTokens = 2048
            },
            safetySettings = listOf(
                SafetySetting(HarmCategory.HARASSMENT, BlockThreshold.MEDIUM_AND_ABOVE),
                SafetySetting(HarmCategory.HATE_SPEECH, BlockThreshold.MEDIUM_AND_ABOVE),
                SafetySetting(HarmCategory.SEXUALLY_EXPLICIT, BlockThreshold.MEDIUM_AND_ABOVE),
                SafetySetting(HarmCategory.DANGEROUS_CONTENT, BlockThreshold.MEDIUM_AND_ABOVE),
            )
        )
        return withContext(Dispatchers.IO) {
            val response = model.generateContent(prompt)
            response.text!!
        }
    }

    fun prompt(prompt: String){
        handler = CoroutineExceptionHandler { _, throwable ->
            println("Exception: ${throwable.localizedMessage}")
        }
        val context: CoroutineContext = Dispatchers.Main + (handler ?: EmptyCoroutineContext)

        job = CoroutineScope(context).launch {
            val generatedText = generateText(prompt)

            println(generatedText)
        }
    }
}
