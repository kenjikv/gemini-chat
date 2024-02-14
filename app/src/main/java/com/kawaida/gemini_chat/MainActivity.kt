package com.kawaida.gemini_chat

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.ai.client.generativeai.GenerativeModel
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var chatRecyclerView: RecyclerView
    private lateinit var questionEditText: EditText
    private lateinit var sendButton: Button
    private val messages = mutableListOf<ChatMessage>()
    private lateinit var chatAdapter: ChatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        questionEditText = findViewById(R.id.questionEditText)
        sendButton = findViewById(R.id.sendButton)
        chatRecyclerView = findViewById(R.id.chatRecyclerView)

        chatAdapter = ChatAdapter(messages)
        chatRecyclerView.adapter = chatAdapter
        chatRecyclerView.layoutManager = LinearLayoutManager(this)

        val generativeModel = GenerativeModel(
            modelName = "gemini-pro",
            apiKey = "YOUR_KEY"
        )

        sendButton.setOnClickListener {
            GlobalScope.launch {
                val prompt = questionEditText.text.toString()
                val response = generativeModel.generateContent(prompt)
                withContext(Dispatchers.Main) {
                    messages.add(ChatMessage(prompt, true))
                    messages.add(ChatMessage(response.text.toString(), false))
                    chatAdapter.notifyDataSetChanged()
                    chatRecyclerView.scrollToPosition(messages.size - 1)
                    questionEditText.setText("")
                }
            }
        }
    }
}
