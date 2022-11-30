package com.example.eventer.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.eventer.R
import com.example.eventer.model.Message
import com.google.firebase.auth.FirebaseAuth

//this class is used to display the messages in the message activity

class MessageAdapter(val context: Context, val messageList: ArrayList<Message>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    val ITEM_RECEIVE = 1
    val ITEM_SEND = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //if viewType is 2 then it is the current user who sent the message
        if (viewType == 2) {
            val view = LayoutInflater.from(context).inflate(R.layout.sent_message, parent, false)
            return SentViewHolder(view)
        } else { //if viewType is 1 then it is the other user who sent the message so we return the receive view holder
            val view =
                LayoutInflater.from(context).inflate(R.layout.received_message, parent, false)
            return ReceivedViewHolder(view)
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentMessage =
            messageList[position] //getting the current message from messageList passed in the constructor

        if (holder.javaClass == SentViewHolder::class.java) { //sent view holder class in below
            val viewHolder = holder as SentViewHolder //type cast to sent view holder
            holder.sentMessage.text =
                currentMessage.message //setting the message in the text view in sent message layout
        } else { //stuff for receive view holder
            val viewHolder = holder as ReceivedViewHolder //type cast to receive view holder
            holder.receivedMessage.text =
                currentMessage.message ////setting the message in the text view in received message layout
        }
    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage =
            messageList[position] //getting the current message from messageList passed in the constructor

        //if the uid of currentt matches with the  sender id of the current message then it is sent message so we inflate sent view holder
        if (FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderId)) {
            return ITEM_SEND
        } else { //else it is received message so we inflate received view holder
            return ITEM_RECEIVE
        }

    }

    override fun getItemCount(): Int {
        return messageList.size
    }


}

class SentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val sentMessage: TextView = itemView.findViewById(R.id.sent_message)
}

class ReceivedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val receivedMessage: TextView = itemView.findViewById(R.id.received_message)
}


