package com.xborg.vendx_service

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    companion object {

        var loadChatMessageFragment = MutableLiveData<Boolean>()

    }

}
