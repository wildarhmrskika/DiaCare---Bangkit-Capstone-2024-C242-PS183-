package com.capstone.diacare.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.capstone.diacare.data.model.ChatEntity
import com.capstone.diacare.databinding.ItemChatBinding
import com.capstone.diacare.utils.MessageType
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class AdapterChat(private val data: List<ChatEntity>) :
    RecyclerView.Adapter<AdapterChat.ViewHolder>() {
    inner class ViewHolder(private val binding: ItemChatBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(item: ChatEntity) {
            binding.apply {
                val type = enumValueOf<MessageType>(item.messageType)
                val zonedDateTime = ZonedDateTime.parse(item.timeStamp)
                val localTime = zonedDateTime.toLocalTime()
                val time = localTime.format(DateTimeFormatter.ofPattern("HH:mm"))
                when (type) {
                    MessageType.BOT -> {
                        leftContainer.visibility = View.VISIBLE
                        rightContainer.visibility = View.GONE

                        tvLeftMessage.text = item.message
                        tvLeftTimestamp.text = time
                    }

                    MessageType.USER -> {
                        leftContainer.visibility = View.GONE
                        rightContainer.visibility = View.VISIBLE

                        tvRightMessage.text = item.message
                        tvRightTimestamp.text = time
                    }
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterChat.ViewHolder {
        val view = ItemChatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: AdapterChat.ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size
}