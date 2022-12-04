package com.example.eventer.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eventer.MessageActivity
import com.example.eventer.R
import com.example.eventer.model.ChatList
import com.example.eventer.model.UsersFb

class UserAdapter : RecyclerView.Adapter<UserAdapter.MyViewHolder>() {


    private var userList = ArrayList<UsersFb>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = userList[position]
        holder.usernameF.text = currentItem.username
        holder.emailF.text = currentItem.email

        //when user clicks on profile of other user then we open message activity
        holder.itemView.setOnClickListener(View.OnClickListener {
            val intent = Intent(holder.itemView.context, MessageActivity::class.java)
            intent.putExtra("name", currentItem.username)
            intent.putExtra("profileId", currentItem.uid)
            holder.itemView.context.startActivity(intent)
        })


    }
    //holder.passwordF.text = currentItem.password


    override fun getItemCount(): Int {
        return userList.size
    }

    fun updateList(newList: List<UsersFb>) {
        this.userList.clear()
        this.userList.addAll(newList)
        notifyDataSetChanged()
    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val usernameF: TextView = itemView.findViewById(R.id.fragment_user_username)
        val emailF: TextView = itemView.findViewById(R.id.fragment_user_email)
        //val passwordF : TextView = itemView.findViewById(R.id.fragment_user_password)
    }


}

