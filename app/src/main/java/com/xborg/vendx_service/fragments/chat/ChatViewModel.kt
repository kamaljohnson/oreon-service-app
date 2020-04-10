package com.xborg.vendx_service.fragments.chat

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.xborg.vendx_service.database.ChatMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job


class ChatViewModel (
    application: Application
): AndroidViewModel(application) {

    val db = FirebaseFirestore.getInstance()

    private val viewModelJob = Job()
    private val ioScope = CoroutineScope(Dispatchers.IO + viewModelJob)

    val chats = MutableLiveData<ArrayList<ChatMessage>>()
    private var userId: String = "2"    //TODO: get the selected userId from home

    init {
        autoLoadChats()
    }

    fun autoLoadChats() {

        chats.postValue(ArrayList())

        db.collection("rooms")
            .document(userId)
            .collection("messages")
            .orderBy("time", Query.Direction.ASCENDING)
            .addSnapshotListener { documents, e ->
                if (e != null) {
                    Log.w("Debug", "Listen failed.", e)
                    return@addSnapshotListener
                }

                val _chats = ArrayList<ChatMessage>()
                for (document in documents!!) {

                    _chats.add(
                        ChatMessage(
                            id = document.id,
                            userId = document["userId"] as String,
                            text = document["text"] as String,
                            time = document["time"] as Timestamp
                        )
                    )
                }
                chats.postValue(_chats)
            }
    }

    fun sendMessageToChat(message: String) {

        val chatMessage: MutableMap<String, Any> = HashMap()
        chatMessage["userId"] = "service"
        chatMessage["text"] = message
        chatMessage["time"] = Timestamp.now()

        db.collection("rooms")
            .document("2")
            .collection("messages")
            .add(chatMessage)
            .addOnSuccessListener { documentReference ->
                Log.d("Debug","DocumentSnapshot added with ID: " + documentReference.id)
            }
            .addOnFailureListener { e -> Log.w("Debug", "Error adding document", e) }

    }
}
