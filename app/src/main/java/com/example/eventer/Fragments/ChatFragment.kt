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
import com.example.eventer.model.UsersViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*


class ChatFragment : Fragment() {
    //for recycler view
    private lateinit var viewModel: UsersViewModel
    private lateinit var chatListAdapter: ChatListAdapter
    private lateinit var chatListRecyclerView: RecyclerView

//    //firebase
//    private lateinit var auth: FirebaseAuth
//    private lateinit var fUser : FirebaseUser
//    private lateinit var databaseReference: DatabaseReference
//
//    //for chat list
//    private var chatList = ArrayList<ChatList>()
//


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        auth = FirebaseAuth.getInstance()
//        databaseReference = FirebaseDatabase.getInstance().reference
//        fUser = auth.currentUser!!
//        var userId = fUser.uid
//
//        //finding recycler view
//        chatListRecyclerView = view?.findViewById(R.id.chatList_recyclerView)!!
//        chatList = ArrayList()
//        chatListAdapter = ChatListAdapter(this, chatList)
//
//
//
//


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
//        //usersList = ArrayList() //initializing userList
//
//        //fUser = FirebaseAuth.getInstance().currentUser!!
//       // databaseReference = FirebaseDatabase.getInstance().getReference("chatList").child(fUser.uid)
//
//        viewModel = ViewModelProvider(this).get(UsersViewModel::class.java)
//        viewModel.allUsers.observe(viewLifecycleOwner, Observer {
//            userAdapter.updateList(it)
//        })
//
//
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chatListRecyclerView = view.findViewById(R.id.chatList_recyclerView)
        chatListRecyclerView.layoutManager = LinearLayoutManager(context)
        chatListRecyclerView.setHasFixedSize(true)
        chatListAdapter = ChatListAdapter()

        chatListRecyclerView.adapter = chatListAdapter

        viewModel = ViewModelProvider(this).get(UsersViewModel::class.java)
    }


    //function to replace fragment
    private fun replaceFragment(fragment: Fragment) {
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.frame_layout, fragment)
        transaction?.commit()
    }


}
