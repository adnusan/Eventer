package com.example.eventer.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.eventer.Adapter.UserAdapter
import com.example.eventer.R
import com.example.eventer.model.ChatList
import com.example.eventer.model.UsersFb
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class ChatFragment : Fragment() {

    private lateinit var userAdapter: UserAdapter
    private lateinit var usersList: List<ChatList>
    private lateinit var chatListRecyclerView: RecyclerView
    private lateinit var fUser : FirebaseUser
    private lateinit var databaseReference: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_chat, container, false)
        val addButton = view.findViewById<View>(R.id.addBtn)
        addButton.setOnClickListener {
            val fragment = UserFragment()
            replaceFragment(fragment)
        }

        return view
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        chatListRecyclerView = view.findViewById(R.id.chatList_recyclerView)
//        chatListRecyclerView.setHasFixedSize(true)
//        userAdapter = UserAdapter()
//        chatListRecyclerView.adapter = userAdapter
//
//        fUser = FirebaseAuth.getInstance().currentUser!!
//        databaseReference = FirebaseDatabase.getInstance().getReference("ChatList").
//
//        usersList = ArrayList()
//    }


    //function to replace fragment
    private fun replaceFragment(fragment: Fragment) {
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.frame_layout, fragment)
        transaction?.commit()
    }


}
