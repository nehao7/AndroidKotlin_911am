package com.o7services.androidkotlin_9_11am.chatbot

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.ai.client.generativeai.GenerativeModel
import com.o7services.androidkotlin_9_11am.databinding.ActivityChatbotBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import kotlin.apply
import kotlin.collections.isNotEmpty
import kotlin.collections.last
import kotlin.let
import kotlin.takeIf
import kotlin.text.isNotBlank
import kotlin.text.isNotEmpty
import kotlin.text.trim

class ChatbotActivity : AppCompatActivity() {

    private val apiKey = "AIzaSyBM9uhtj5rCJaR_hAkuoSnaQDy0nP4ewEU"
    private lateinit var binding: ActivityChatbotBinding
    private val messageList = mutableListOf<MessageModel>()
    private lateinit var messageAdapter: ChatbotMessageAdapter
    private val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
        .build()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityChatbotBinding.inflate(layoutInflater)
        setContentView(binding.root)

        messageAdapter = ChatbotMessageAdapter(messageList)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = this.messageAdapter

        with(binding) {


            binding.sendBtn.setOnClickListener {
                val question = messageEt.text.toString().trim { it <= ' ' }
                if (question.isNotEmpty()) {
                    addToChat(question, MessageModel.SENT_BY_ME)
                    addTypingIndicator()
                    messageEt.text.clear()
                    callAPI(question)
                    welcomeText.visibility = View.GONE
                }
            }
        }

    }


    private fun addToChat(message: String, sentBy: String) {
        runOnUiThread {
            messageList.add(MessageModel(message, sentBy))
            messageAdapter.notifyItemInserted(messageList.size - 1)
            binding.recyclerView.smoothScrollToPosition(messageAdapter.itemCount - 1)
        }
    }
    private fun addTypingIndicator() {
        messageList.add(MessageModel("Typing...", MessageModel.SENT_BY_BOT))
        messageAdapter.notifyItemInserted(messageList.size - 1)
    }


    private fun addResponse(response: String?) {
        // Safely check if the last message is "Typing..." and remove it.
        if (messageList.isNotEmpty() && messageList.last().message == "Typing...") {
            val removePosition = messageList.size - 1
            messageList.removeAt(removePosition)
            messageAdapter.notifyItemRemoved(removePosition)
        }

        // Add the actual response, ensuring it's not null or blank.
        response?.takeIf { it.isNotBlank() }?.let {
            messageList.add(MessageModel(it, MessageModel.SENT_BY_BOT))
            messageAdapter.notifyItemInserted(messageList.size - 1)
        }

        // Ensure the RecyclerView scrolls to show the latest added message.
        binding.recyclerView.scrollToPosition(messageList.size - 1)
    }

    private fun callAPI(question:String){
        val generativeModel = GenerativeModel(
            // For text-only input, use the gemini-pro model
            modelName = "gemini-2.0-flash",
            // Access your API key as a Build Configuration variable (see "Set up your API key" above)
            apiKey = apiKey
        )
        runBlocking {
            launch {

                try {
                    delay(1000)
                    val response = generativeModel.generateContent(question).text
                    addResponse(response ?: "Oops! I couldn’t understand that.")
                } catch (e: Exception) {
                    addResponse("Error: ${e.message}")
                }
            }
        }

    }
}