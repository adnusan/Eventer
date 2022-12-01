package com.example.eventer.Adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eventer.MessageActivity
import com.example.eventer.R
import com.example.eventer.model.ChatList
import com.example.eventer.model.UsersFb
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ChatListAdapter : RecyclerView.Adapter<ChatListAdapter.ChatListViewHolder>() {

    private var databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference("user")
    private var chatList = ArrayList<ChatList>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatListViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.chat_list_item, parent, false)
        return ChatListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ChatListViewHolder, position: Int) {
        val currentItem = chatList[position]
        holder.chat_list_item_username.text = currentItem.receiverId

        //holder.itemView.setOnClickListener(View.OnClickListener {
            //val intent = Intent(holder.itemView.context, MessageActivity::class.java)
            //val username  =
               // currentItem.receiverId?.let { it1 -> databaseReference.child(it1).child("username").toString() }
//            intent.putExtra("name", username)
//            intent.putExtra("profileId", currentItem.receiverId)
//            Log.d("ChatListAdapter", "onBindViewHolder: $username")
//            holder.itemView.context.startActivity(intent)
       // })
    }

    override fun getItemCount(): Int {
        return chatList.size
    }

    fun updateChatList(newList: List<ChatList>) {
        this.chatList.clear()
        this.chatList.addAll(newList)
        notifyDataSetChanged()
    }
    class ChatListViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {
        val chat_list_item_username: TextView = itemView.findViewById<TextView>(R.id.chatList_item_username)

    }




}







