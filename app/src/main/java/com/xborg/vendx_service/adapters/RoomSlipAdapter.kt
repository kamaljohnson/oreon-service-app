package com.xborg.vendx_service.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.xborg.vendx_service.R
import com.xborg.vendx_service.SharedViewModel
import com.xborg.vendx_service.database.Room
import com.xborg.vendx_service.fragments.home.HomeViewModel
import kotlinx.android.synthetic.main.room_slip.view.*
import java.text.SimpleDateFormat


private var TAG = "RoomSlipAdapter"

class RoomSlipAdapter(
    val context: Context
) : ListAdapter<Room, RoomSlipAdapter.RoomSlipViewHolder>(RoomSlipDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomSlipViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.room_slip, parent, false)

        return RoomSlipViewHolder(view)
    }

    @SuppressLint("SimpleDateFormat", "ResourceAsColor")
    override fun onBindViewHolder(holder: RoomSlipViewHolder, position: Int) {
        val room = getItem(position)

        holder.userId.text = room.userId

        val sfd = SimpleDateFormat("HH:mm")
        holder.lastActiveTime.text = sfd.format(room.lastActiveTime.toDate());
    }

    class RoomSlipViewHolder(view: View) :
        RecyclerView.ViewHolder(view), View.OnClickListener {

        var userId: TextView = view.user_id as TextView
        var lastActiveTime: TextView = view.last_active_time as TextView

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            Log.i("Debug", "clicked" + userId.text.toString())
            HomeViewModel.selectedRoomUserId.value = userId.text.toString()
            SharedViewModel.loadChatMessageFragment.value = true
        }
    }
}

class RoomSlipDiffCallback: DiffUtil.ItemCallback<Room>() {
    override fun areItemsTheSame(oldItem: Room, newItem: Room): Boolean {
        return oldItem.userId == newItem.userId
    }

    override fun areContentsTheSame(oldItem: Room, newItem: Room): Boolean {
        return oldItem == newItem
    }
}

