package com.example.lotus.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lotus.databinding.ItemChatBinding
import com.example.lotus.data.model.Chat
import com.example.lotus.ui.viewModel.ChatViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChatAdapter : ListAdapter<Chat, ChatAdapter.ViewHolder>((DiffUtilCallback())) {

    class DiffUtilCallback : DiffUtil.ItemCallback<Chat>() {
        override fun areItemsTheSame(oldItem: Chat, newItem: Chat): Boolean {
            return oldItem.userId == newItem.userId
        }

        override fun areContentsTheSame(oldItem: Chat, newItem: Chat): Boolean {
            return oldItem.message == newItem.message
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemChatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = ViewHolder(binding)
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(val binding: ItemChatBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(chat: Chat) {
            CoroutineScope(Dispatchers.IO).launch {
                val chatUser = ChatViewModel().getCurrentUserId(chat.userId)
                withContext(Dispatchers.Main) {
                    binding.apply {
//                        Glide.with(itemView)
//                            .load(chatUser.image)
//                            .placeholder(R.drawable.ic_launcher_foreground)
//                            .into(profileIV)

                        messageTV.text = chat.message
                    }
                }
            }
        }
    }
}