package com.example.eventer.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.eventer.Repository.UserRepository


class UsersViewModel : ViewModel() {
    private val repository: UserRepository = UserRepository().getInstance()
    private val _allUsers = MutableLiveData<List<UsersFb>>()
    val allUsers: MutableLiveData<List<UsersFb>> = _allUsers


    init {
        repository.loadUsers(_allUsers)
    }


}