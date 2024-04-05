package com.turgayozdemir.foodchat.classic

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.turgayozdemir.foodchat.R
import com.turgayozdemir.foodchat.databinding.ItemOtherMessageBinding
import com.turgayozdemir.foodchat.databinding.ItemUserMessageBinding
import com.turgayozdemir.foodchat.databinding.StaticMessageItemBinding

class ChatAdapter(private var messages: MutableList<Chat>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_STATIC = 0
        private const val VIEW_TYPE_USER_MESSAGE = 1
        private const val VIEW_TYPE_OTHER_MESSAGE = 2
    }

    val initialMessage = Chat(
        prompt = "Günaydın, bugün sana nasıl yardımcı olabilirim?",
        isFromUser = false,
        isStatic = true // Bu mesaj sabit bir mesaj
    )

    override fun getItemViewType(position: Int): Int {
        val message = messages[position]
        Log.e("BUSRA", message.isStatic.toString())
        return when {
            message.isStatic -> VIEW_TYPE_STATIC
            message.isFromUser -> VIEW_TYPE_USER_MESSAGE
            else -> VIEW_TYPE_OTHER_MESSAGE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_USER_MESSAGE -> {
                val binding = ItemUserMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                UserMessageViewHolder(binding)
            }
            VIEW_TYPE_OTHER_MESSAGE -> {
                val binding = ItemOtherMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                OtherMessageViewHolder(binding)
            }
            VIEW_TYPE_STATIC -> {
                val binding = StaticMessageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                StaticMessageViewHolder(binding)
            }

            else -> throw IllegalArgumentException("There is a problem in Adapter view holder.")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messages[position]
        if (holder is UserMessageViewHolder) {
            holder.bind(message)
        } else if (holder is OtherMessageViewHolder) {
            holder.bind(message)
        } else if( holder is StaticMessageViewHolder) {
            holder.bind(message)
        }
    }

    override fun getItemCount(): Int = messages.size

    fun addMessage(chatMessage: Chat) {
        messages.add(chatMessage)
        notifyItemInserted(messages.size - 1)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateMessages(newMessages: List<Chat>) {
        messages.clear()
        messages.add(0,initialMessage)
        messages.addAll(newMessages)
        notifyDataSetChanged()
    }



    class UserMessageViewHolder(private val binding: ItemUserMessageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(message: Chat) {
            binding.messageText.text = message.prompt
            val userMessageBackground = ContextCompat.getDrawable(binding.root.context, R.drawable.sent_message)
            binding.messageText.background = userMessageBackground


        }
    }

    class OtherMessageViewHolder(private val binding: ItemOtherMessageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(message: Chat) {
            binding.messageText.text = message.prompt
            val otherMessageBackground = ContextCompat.getDrawable(binding.root.context, R.drawable.received_message)
            binding.messageText.background = otherMessageBackground
        }
    }

    class StaticMessageViewHolder(private val binding: StaticMessageItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(message: Chat) {
            binding.messageTextStatic.text= message.prompt
        }
    }


}
