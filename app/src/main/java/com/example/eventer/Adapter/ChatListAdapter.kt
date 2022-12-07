package com.example.eventer.Adapter

import android.content.Context
import android.content.Intent
import android.text.TextUtils.substring
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eventer.MessageActivity
import com.example.eventer.R
import com.example.eventer.model.ChatList
import com.example.eventer.model.Message
import com.example.eventer.model.UsersFb

class ChatListAdapter(val context: Context, val chatList: ArrayList<ChatList>) :
    RecyclerView.Adapter<ChatListAdapter.ChatListViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.chat_list_users, parent, false)
        return ChatListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatListViewHolder, position: Int) {
        val currentItem = chatList[position]
        holder.username.text = currentItem.name
        holder.usernameInitial.text = substring(currentItem.name, 0, 1).uppercase()

        //when user clicks on profile of other user then we open message activity
        holder.itemView.setOnClickListener(View.OnClickListener {
            val intent = Intent(holder.itemView.context, MessageActivity::class.java)
            intent.putExtra("name", currentItem.name)
            intent.putExtra("profileId", currentItem.senderId)
            holder.itemView.context.startActivity(intent)
        })

    }

    override fun getItemCount(): Int {
        return chatList.size
    }

    fun updateChatList(newList: List<ChatList>) {
        this.chatList.clear()
        this.chatList.addAll(newList)
        notifyDataSetChanged()
    }



    class ChatListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val usernameInitial: TextView = itemView.findViewById(R.id.chat_list_username_Initial)
        val username: TextView = itemView.findViewById(R.id.fragment_user_username)


    }

}