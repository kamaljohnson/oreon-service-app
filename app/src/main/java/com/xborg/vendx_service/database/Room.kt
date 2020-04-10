package com.xborg.vendx_service.database

import com.google.firebase.Timestamp

data class Room(
    val userId: String,
    val lastActiveTime: Timestamp
)