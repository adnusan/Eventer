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
import com.example.eventer.model.FriendListModel
import com.example.eventer.model.Message
import com.example.eventer.model.UsersFb

class FriendListAdapter(val context: Context, val friendList: ArrayList<FriendListModel>)
    : RecyclerView.Adapter<FriendListAdapter.FriendListViewHolder>() {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.friendlist_item, parent, false)
        return FriendListViewHolder(view)
    }

    override fun onBindViewHolder(holder: FriendListViewHolder, position: Int) {
        val currentFriendList = friendList[position]
        holder.friendNameInitial.text =  substring(currentFriendList.name, 0, 1).uppercase()
        holder.friendUsername.text = currentFriendList.name

        holder.itemView.setOnClickListener(View.OnClickListener {
            val intent = Intent(holder.itemView.context, MessageActivity::class.java)
            intent.putExtra("name", currentFriendList.name)
            intent.putExtra("profileId", currentFriendList.friendId)
            holder.itemView.context.startActivity(intent)
        })
    }

    override fun getItemCount(): Int {
        return friendList.size
    }

    class FriendListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val friendNameInitial: TextView = itemView.findViewById(R.id.friendlist_username_Initial)
        val friendUsername: TextView = itemView.findViewById(R.id.friendlist_username)
    }



}