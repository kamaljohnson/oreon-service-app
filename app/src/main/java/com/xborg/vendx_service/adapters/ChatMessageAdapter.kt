package com.xborg.vendx_service.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.xborg.vendx_service.R
import com.xborg.vendx_service.database.ChatMessage
import kotlinx.android.synthetic.main.chat_message.view.*
import java.text.SimpleDateFormat


private var TAG = "ChatMessageAdapter"

class ChatMessageAdapter(
    val context: Context
) : ListAdapter<ChatMessage, ChatMessageAdapter.ChatMessageViewHolder>(ChatMessageDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatMessageViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.chat_message, parent, false)

        return ChatMessageViewHolder(view)
    }

    @SuppressLint("SimpleDateFormat", "ResourceAsColor")
    override fun onBindViewHolder(holder: ChatMessageViewHolder, position: Int) {
        val chatMessage = getItem(position)

        holder.text.text = chatMessage.text

        val sfd = SimpleDateFormat("HH:mm")
        holder.time.text = sfd.format(chatMessage.time.toDate());

        holder.userId = chatMessage.userId

        if(holder.userId != "service") {

            holder.messageBackground.setBackgroundResource(R.drawable.drawable_message_box_receive);
            val params = holder.messageBackground.layoutParams as RelativeLayout.LayoutParams
            params.removeRule(RelativeLayout.ALIGN_PARENT_END);
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT)
            holder.messageBackground.layoutParams = params

        } else {

            holder.messageBackground.setBackgroundResource(R.drawable.drawable_message_box_send);
            val params = holder.messageBackground.layoutParams as RelativeLayout.LayoutParams
            params.removeRule(RelativeLayout.ALIGN_PARENT_LEFT);
            params.addRule(RelativeLayout.ALIGN_PARENT_END)
            holder.messageBackground.layoutParams = params

        }
    }

    class ChatMessageViewHolder(view: View) :
        RecyclerView.ViewHolder(view), View.OnClickListener {

        val messageBackground: LinearLayout = view.message_background
        val time: TextView = view.time
        val text: TextView = view.message_text

        var userId: String = ""

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
        }

    }
}

class ChatMessageDiffCallback: DiffUtil.ItemCallback<ChatMessage>() {
    override fun areItemsTheSame(oldItem: ChatMessage, newItem: ChatMessage): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ChatMessage, newItem: ChatMessage): Boolean {
        return oldItem == newItem
    }
}

