package com.o7services.androidkotlin_9_11am.chatbot

class MessageModel(var message: String, var sentBy: String) {
    companion object {
        const val SENT_BY_ME = "Me"
        const val SENT_BY_BOT = "Bot"
    }
}