package com.example.eventer.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eventer.Adapter.ChatListAdapter
import com.example.eventer.Adapter.UserAdapter
import com.example.eventer.R
import com.example.eventer.model.ChatList
import com.example.eventer.model.ChatListViewModel
import com.example.eventer.model.UsersFb
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class ChatFragment : Fragment() {


    private lateinit var userAdapter: ChatListAdapter
    private lateinit var mUsers: List<UsersFb>
    private lateinit var chatListRecyclerView: RecyclerView
    private lateinit var chatListReference: DatabaseReference
    private lateinit var chatListViewModel: ChatListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_chat, container, false)

        val addButton = view.findViewById<View>(R.id.addBtn)

        addButton.setOnClickListener {
            val fragment = UserFragment()
            replaceFragment(fragment)
        }


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        // Inflate the layout for this fragment
        chatListRecyclerView = view.findViewById(R.id.chatList_recyclerView)
        chatListRecyclerView.setHasFixedSize(true)
        chatListRecyclerView.layoutManager = LinearLayoutManager(context)
        userAdapter = ChatListAdapter()
        mUsers = ArrayList()
        chatListRecyclerView.adapter = userAdapter

        chatListReference = FirebaseDatabase.getInstance().getReference("Users")

        chatListViewModel = ViewModelProvider(this).get(ChatListViewModel::class.java)

        chatListViewModel.allChatList.observe(viewLifecycleOwner, Observer {
            userAdapter.updateChatList(it)
        })

    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.frame_layout, fragment)
        transaction?.commit()
    }


}
