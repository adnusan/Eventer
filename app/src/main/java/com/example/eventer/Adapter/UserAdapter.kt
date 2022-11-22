package com.example.eventer.Adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eventer.R
import com.example.eventer.model.UsersFb

class UserAdapter : RecyclerView.Adapter<UserAdapter.MyViewHolder>() {


    private var userList = ArrayList<UsersFb>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = userList[position]
        holder.usernameF.text = currentItem.username
        holder.emailF.text = currentItem.email
        holder.passwordF.text = currentItem.password

    }

    override fun getItemCount(): Int {
        return userList.size
    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val usernameF: TextView = itemView.findViewById(R.id.fragment_user_username)
        val emailF: TextView = itemView.findViewById(R.id.fragment_user_email)
        val passwordF : TextView = itemView.findViewById(R.id.fragment_user_password)
    }

    fun updateList(newList: List<UsersFb>){
        this.userList.clear()
        this.userList.addAll(newList)
        notifyDataSetChanged()
    }




}

