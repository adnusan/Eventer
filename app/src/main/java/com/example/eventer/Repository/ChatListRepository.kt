package com.example.eventer.Repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.eventer.model.ChatList
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

//class to get chat list data from firebase

class ChatListRepository {
    //firebase initialization
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var firebaseUser: FirebaseUser? = auth.currentUser
    private var databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference("chatList")
        .child(firebaseUser!!.uid)

    @Volatile
    private var CHAT_INSTANCE: ChatListRepository? = null

    fun getInstance(): ChatListRepository {
        return CHAT_INSTANCE ?: synchronized(this) {
            val inst = ChatListRepository()
            CHAT_INSTANCE = inst
            inst
        }
    }


    fun loadChatList(chatList: MutableLiveData<List<ChatList>>) {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    val updatedChatList: List<ChatList> =
                        snapshot.children.mapNotNull { dataSnapshot ->
                            dataSnapshot.getValue(ChatList::class.java)
                        }
                    chatList.postValue(updatedChatList)
                    Log.d("ChatListRepository", "ChatList: $updatedChatList")
                    println("ChatList: $updatedChatList")
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


