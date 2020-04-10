package com.xborg.vendx_service

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.xborg.vendx_service.fragments.chat.ChatFragment
import com.xborg.vendx_service.fragments.home.HomeFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, HomeFragment.newInstance())
                .commitNow()
        }

        SharedViewModel.loadChatMessageFragment.observe(this, Observer { load ->
            if(load) {
                loadChatMessageFragment()
            }
        })

    }

    private fun loadChatMessageFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, ChatFragment.newInstance())
            .commitNow()
    }

    private fun goBack() {
        finish()
    }
}
