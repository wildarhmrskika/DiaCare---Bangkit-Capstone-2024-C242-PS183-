package com.capstone.diacare.presentation.home

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.diacare.adapter.AdapterChat
import com.capstone.diacare.data.model.ChatEntity
import com.capstone.diacare.databinding.FragmentChatBinding

class ChatFragment : Fragment() {
    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapterChat: AdapterChat
    private val chatData = arrayListOf<ChatEntity>()

    private val vm : HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        adapterChat = AdapterChat(chatData)
        binding.rvChat.adapter = adapterChat
        binding.rvChat.layoutManager = LinearLayoutManager(requireContext())
        vm.loadMessages()
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.onGetChatData.observe(requireActivity()){
            chatData.clear()
            chatData.addAll(it)
            adapterChat.notifyItemInserted(chatData.size-1)
            binding.rvChat.scrollToPosition(chatData.size-1)
        }

        binding.btnSend.setOnClickListener {
            val message = binding.etMessage.text.toString()
            vm.sendMessage(message)
            binding.etMessage.text.clear()
        }
    }
}