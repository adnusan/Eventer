package com.example.eventer.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eventer.Adapter.ChatListAdapter
import com.example.eventer.Adapter.UserAdapter
import com.example.eventer.R
import com.example.eventer.model.ChatList
import com.example.eventer.model.UsersFb
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*


class ChatFragment : Fragment() {


    private lateinit var chatListAdapter: ChatListAdapter
    private var mUsers = mutableListOf<UsersFb>()
    private var usersList =  mutableListOf<ChatList>()

    private lateinit var chatListRecyclerView: RecyclerView

    private lateinit var fuser: FirebaseUser
    private lateinit var databasetReference: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //messed up main branch



    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_chat, container, false)


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
         //Inflate the layout for this fragment
        chatListRecyclerView = view.findViewById(R.id.chatlist_recyclerView)
        chatListRecyclerView.setHasFixedSize(true)
        chatListRecyclerView.layoutManager = LinearLayoutManager(context)

        fuser = FirebaseAuth.getInstance().currentUser!!

        mUsers = ArrayList()
        usersList = ArrayList()
        databasetReference = FirebaseDatabase.getInstance().getReference("chatList").child(fuser.uid)

        databasetReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                usersList.clear()
                for (dataSnapshot in snapshot.children) {
                    val chatList = dataSnapshot.getValue(ChatList::class.java)
                    usersList.add(chatList!!)
                }
                loadChatList() //calling loadChatList method
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

        val addButton = view.findViewById<View>(R.id.addBtn)
        addButton.setOnClickListener {
            val fragment = UserFragment()
            replaceFragment(fragment)
        }

    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.frame_layout, fragment)
        transaction?.commit()
    }

    //method to load chat list, it loads content using chatListAdapter
    private fun loadChatList(){
        chatListAdapter = ChatListAdapter(requireContext(), usersList as ArrayList<ChatList>)
        chatListRecyclerView.adapter = chatListAdapter

    }


}
