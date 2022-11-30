package com.example.eventer.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.eventer.Repository.ChatListRepository

class ChatListViewModel: ViewModel() {
    private val repository: ChatListRepository = ChatListRepository().getInstance()
    private val _allChatList = MutableLiveData<List<ChatList>>()
    val allChatList : MutableLiveData<List<ChatList>> = _allChatList

    init {
        repository.loadChatList(_allChatList)
    }

}
