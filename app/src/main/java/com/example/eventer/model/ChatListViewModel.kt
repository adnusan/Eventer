package com.example.eventer.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.eventer.Repository.ChatListRepository
import com.example.eventer.Repository.UserRepository

class ChatListViewModel : ViewModel() {
    private val repository: ChatListRepository = ChatListRepository().getInstance()
    private val _allChatList = MutableLiveData<List<ChatList>>()
    val allUsers: MutableLiveData<List<ChatList>> = _allChatList


    init {
        repository.loadChatList(_allChatList)
    }


}