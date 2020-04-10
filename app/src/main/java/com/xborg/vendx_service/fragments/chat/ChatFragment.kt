package com.xborg.vendx_service.fragments.chat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.xborg.vendx_service.R
import com.xborg.vendx_service.adapters.ChatMessageAdapter
import kotlinx.android.synthetic.main.chat_fragment.*

class ChatFragment : Fragment() {

    companion object {
        fun newInstance() = ChatFragment()
    }

    private lateinit var viewModel: ChatViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.chat_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val viewModelFactory = ChatViewModelFactory(activity!!.application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ChatViewModel::class.java)

        val chatMessageAdapter = ChatMessageAdapter(context!!)

        messageRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = chatMessageAdapter
        }

        viewModel.chats.observe(viewLifecycleOwner, Observer { chats ->
            if(chats != null) {
                chatMessageAdapter.submitList(chats)
                progressBar.visibility = View.GONE
                messageRecyclerView.smoothScrollToPosition(chats.count())
            }
        })

        sendButton.setOnClickListener {

            val message = messageEditText.text.toString()
            if(message != "") {
                viewModel.sendMessageToChat(message)

                messageEditText.setText("")
                messageRecyclerView.smoothScrollToPosition(viewModel.chats.value!!.count())
            }

        }

    }

}
