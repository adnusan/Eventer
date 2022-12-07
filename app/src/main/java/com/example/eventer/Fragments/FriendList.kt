package com.example.eventer.Fragments


import com.google.firebase.database.*


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eventer.Adapter.FriendListAdapter
import com.example.eventer.R
import com.example.eventer.model.FriendListModel
import com.example.eventer.model.UsersFb
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class FriendList : Fragment() {

    private lateinit var friendListAdapter: FriendListAdapter
    private var mUsers = mutableListOf<UsersFb>()
    private var friendList =  mutableListOf<FriendListModel>()

    private lateinit var friendListRecyclerView: RecyclerView

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
        return inflater.inflate(R.layout.fragment_friend_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        friendListRecyclerView = view.findViewById(R.id.friendlist_recyclerView)
        friendListRecyclerView.setHasFixedSize(true)
        friendListRecyclerView.layoutManager = LinearLayoutManager(context)

        fuser = FirebaseAuth.getInstance().currentUser!!

        mUsers = ArrayList()
        friendList = ArrayList()
        databaseReference = FirebaseDatabase.getInstance().getReference("friendsList").child(fuser.uid)

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                friendList.clear()
                for (data in snapshot.children) {
                    val friend = data.getValue(FriendListModel::class.java)
                    if (friend != null) {
                        friendList.add(friend)
                    }
                }
                loadFriendList()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })


    }


    private fun loadFriendList(){
        friendListAdapter = FriendListAdapter(requireContext(), friendList as ArrayList<FriendListModel>)
        friendListRecyclerView.adapter = friendListAdapter
    }




}