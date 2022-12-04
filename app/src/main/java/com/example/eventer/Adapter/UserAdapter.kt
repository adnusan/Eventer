package com.example.eventer.Adapter

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.eventer.Fragments.AddFriends
import com.example.eventer.MessageActivity
import com.example.eventer.R
import com.example.eventer.model.ChatList
import com.example.eventer.model.UsersFb
import com.google.firebase.database.DatabaseReference

class UserAdapter : RecyclerView.Adapter<UserAdapter.MyViewHolder>() {


    private var userList = ArrayList<UsersFb>()
    val addFreidndData = Bundle()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = userList[position]
        val addButton = holder.itemView.findViewById<Button>(R.id.fragment_add_button)
        holder.usernameF.text = currentItem.username
        holder.emailF.text = currentItem.email

        //when user clicks on profile of other user then we open message activity
        holder.itemView.setOnClickListener(View.OnClickListener {

            val intent = Intent(holder.itemView.context, MessageActivity::class.java)
            intent.putExtra("name", currentItem.username)
            intent.putExtra("profileId", currentItem.uid)
            holder.itemView.context.startActivity(intent)
        })
        addButton.setOnClickListener(View.OnClickListener {
            Toast.makeText(holder.itemView.context, "Friend added", Toast.LENGTH_SHORT).show()
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

