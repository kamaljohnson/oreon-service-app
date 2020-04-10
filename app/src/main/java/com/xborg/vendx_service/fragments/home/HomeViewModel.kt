package com.xborg.vendx_service.fragments.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.xborg.vendx_service.database.ChatMessage
import com.xborg.vendx_service.database.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class HomeViewModel (
    application: Application
): AndroidViewModel(application) {

    companion object {

        var selectedRoomUserId = MutableLiveData<String>()

    }

    val db = FirebaseFirestore.getInstance()

    val rooms = MutableLiveData<ArrayList<Room>>()

    init {
        autoLoadRooms()
    }

    fun autoLoadRooms() {

        rooms.postValue(ArrayList())

        db.collection("rooms")
            .orderBy("lastActiveTime", Query.Direction.DESCENDING)
            .addSnapshotListener { documents, e ->
                if (e != null) {
                    Log.w("Debug", "Listen failed.", e)
                    return@addSnapshotListener
                }

                val _rooms = ArrayList<Room>()
                for (document in documents!!) {

                    _rooms.add(
                        Room(
                            userId = document.id,
                            lastActiveTime = document["lastActiveTime"] as Timestamp
                        )
                    )
                }
                rooms.postValue(_rooms)
            }
    }
}
