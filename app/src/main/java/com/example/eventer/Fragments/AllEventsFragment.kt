package com.example.eventer.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eventer.Adapter.EventListAdapter
import com.example.eventer.R
import com.example.eventer.model.Event
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*


class AllEventsFragment : Fragment() {
    private lateinit var alleventListRecyclerView: RecyclerView
    private lateinit var alleventListAdapter: EventListAdapter
    private var alleventList = mutableListOf<Event>()
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
        return inflater.inflate(R.layout.fragment_all_events, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        alleventListRecyclerView = view.findViewById(R.id.allevent_recyclerview)
        alleventListRecyclerView.setHasFixedSize(true)
        alleventListRecyclerView.layoutManager = LinearLayoutManager(context)

        fuser = FirebaseAuth.getInstance().currentUser!!
        val userId = fuser.uid.toString()

        alleventList = ArrayList()
        databaseReference = FirebaseDatabase.getInstance().getReference("events")

        //getting all the events from the database
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                alleventList.clear()
                for (events in snapshot.children) {

                    val event = events.children
                    for (data in event) {
                        val temp = data.getValue(Event::class.java)
                        if (temp != null) {
                            alleventList.add(temp)
                        }
                    }
                }
                loadEventList()
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })


    }

    fun  loadEventList() {
        alleventListAdapter = EventListAdapter(requireContext(),alleventList as ArrayList<Event>)
        alleventListRecyclerView.adapter = alleventListAdapter
    }
}
