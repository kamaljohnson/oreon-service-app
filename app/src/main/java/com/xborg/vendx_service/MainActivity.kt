package com.xborg.vendx_service

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.xborg.vendx_service.fragments.chat.ChatFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, ChatFragment.newInstance())
                .commitNow()
        }
    }

    private fun goBack() {
        finish()
    }
}
