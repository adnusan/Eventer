package com.example.eventer.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eventer.Adapter.EventListAdapter
import com.example.eventer.Adapter.FriendListAdapter
import com.example.eventer.R
import com.example.eventer.model.Event
import com.example.eventer.model.FriendListModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*


class EventListFragment : Fragment() {
    private lateinit var eventListRecyclerView: RecyclerView
    private lateinit var eventListAdapter: EventListAdapter
    private var eventList = ArrayList<Event>()
    //firebase
    private lateinit var fuser: FirebaseUser
    private lateinit var databaseReference: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eventListRecyclerView = view.findViewById(R.id.eventlist_recyclerview)
        eventListRecyclerView.setHasFixedSize(true)
        eventListRecyclerView.layoutManager = LinearLayoutManager(context)

        fuser = FirebaseAuth.getInstance().currentUser!!

        eventList = ArrayList()
        databaseReference = FirebaseDatabase.getInstance().getReference("events").child(fuser.uid)

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                eventList.clear()
                for (snapshot in snapshot.children) {
                    val event = snapshot.getValue(Event::class.java)
                    eventList.add(event!!)
                }
                loadEventList()
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })


    }

    fun  loadEventList() {
        eventListAdapter = EventListAdapter(requireContext(),eventList)
        eventListRecyclerView.adapter = eventListAdapter
    }


}