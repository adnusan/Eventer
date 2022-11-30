package com.example.eventer.Repository

import androidx.lifecycle.MutableLiveData
import com.example.eventer.model.ChatList
import com.google.firebase.database.*

class ChatListRepository {
    private val database: DatabaseReference = FirebaseDatabase.getInstance().getReference("chatlist")
    private var instance: ChatListRepository? = null

    fun getInstance(): ChatListRepository {
        if (instance == null) {
            instance = ChatListRepository()
        }
        return instance!!
    }

    fun loadChatList(chatList: MutableLiveData<List<ChatList>>) {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    val updatedChatList: List<ChatList> =
                        snapshot.children.mapNotNull { dataSnapshot ->
                            dataSnapshot.getValue(ChatList::class.java)
                        }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                //do nothing
            }
        })
    }
}