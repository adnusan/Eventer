package com.example.eventer.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eventer.Adapter.UserAdapter
import com.example.eventer.Adapter.UsersFb
import com.example.eventer.R
import com.example.eventer.database.FirebaseClass
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Users_fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Users_fragment : Fragment() {

    private lateinit var userRecyclerView: RecyclerView
    private lateinit var UserAdapter: UserAdapter
    var userEmail = ArrayList<String>()
    var username = ArrayList<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }
    //val firebaseClass = FirebaseClass()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_users_fragment, container, false)

        userRecyclerView = view.findViewById(R.id.recyclerView)
        userRecyclerView.layoutManager = LinearLayoutManager(context)
        userRecyclerView.setHasFixedSize(true)

        readUsers()





        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Users_fragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Users_fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun readUsers() {
        val firebaseClass = FirebaseClass()

        //firebase initialization
        firebaseClass.auth = Firebase.auth
        firebaseClass.database = Firebase.database.reference
        firebaseClass.firebaseUser = firebaseClass.auth.currentUser!!

        firebaseClass.database =
            Firebase.database.reference.child("Users").child(firebaseClass.firebaseUser.uid)

        firebaseClass.database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (userSnapshot in snapshot.children) {
                        val user = userSnapshot.getValue(UsersFb::class.java)
                        if (user != null) {
                            username.add(user.username)
                            userEmail.add(user.uid)
                        }
                    }
                    UserAdapter = UserAdapter(username, userEmail)
                    userRecyclerView.adapter = UserAdapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        }


    }
