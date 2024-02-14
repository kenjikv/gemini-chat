package com.kawaida.gemini_chat

data class ChatRequest(val prompt: String, val temperature: Double, val max_tokens: Int)

data class ChatResponse(val choices: List<Choice>)

data class Choice(val text: String)

data class ChatMessage(
    val content: String,
    val isUserMessage: Boolean
)

