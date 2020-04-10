package com.xborg.vendx_service.database

import com.google.firebase.Timestamp

data class ChatMessage(
    val id: String,
    val userId: String,
    val text: String,
    val time: Timestamp
)