package com.example.eventer.Adapter

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eventer.R
import com.example.eventer.model.Event
import com.example.eventer.model.FriendListModel

class EventListAdapter(val context: Context, val eventList: ArrayList<Event>)
    : RecyclerView.Adapter<EventListAdapter.EventListViewHolder>() {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.eventlist_item, parent, false)
        return EventListViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventListViewHolder, position: Int) {
        val currentEventList = eventList[position]
        holder.usernameEvent.text = currentEventList.username
        holder.title.text = currentEventList.title
        holder.latitude.text = currentEventList.latitude.toString()
        holder.longitude.text = currentEventList.longitude.toString()


    }

    override fun getItemCount(): Int {
        return eventList.size
    }

    class EventListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val usernameEvent = itemView.findViewById<TextView>(R.id.eventlist_username_fragment)
        val title = itemView.findViewById<TextView>(R.id.eventlist_title_fragment)
        val longitude = itemView.findViewById<TextView>(R.id.eventlist_longitude_fragment)
        val latitude = itemView.findViewById<TextView>(R.id.eventlist_latitude_fragment)
    }



}