package com.turgayozdemir.foodchat.classic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.turgayozdemir.foodchat.databinding.FragmentFoodChatClassicBinding

class FoodChatClassicFragment: Fragment() {
    private var _binding: FragmentFoodChatClassicBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ChatVM
    private lateinit var chatAdapter: ChatAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFoodChatClassicBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = ChatVMFactory()
        viewModel = ViewModelProvider(this, factory)[ChatVM::class.java]

        setupRecyclerView()
        setupMessageInput()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.reverseLayout = true
        layoutManager.stackFromEnd = true
        binding.chatRecyclerView.layoutManager = layoutManager

        chatAdapter = ChatAdapter(mutableListOf())
        binding.chatRecyclerView.adapter = chatAdapter
    }

    private fun setupMessageInput() {
        binding.sendButton.setOnClickListener {
            val message = binding.messageInput.text.toString()
            viewModel.onEvent(ChatUiEvent.SendPrompt(message))
            binding.messageInput.text?.clear()
        }
    }

    private fun observeViewModel() {
        viewModel.chatState.asLiveData().observe(viewLifecycleOwner) { chatState ->
            // if (chatState.chatList.isNotEmpty()) chatAdapter.addMessage(chatState.chatList.first())
            chatAdapter.updateMessages(chatState.chatList)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}