package com.example.eventer.Fragments

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.eventer.Login
import com.example.eventer.R
import com.example.eventer.model.UsersFb
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

private lateinit var auth: FirebaseAuth
private lateinit var databaseReference: DatabaseReference
private lateinit var user: FirebaseUser
private lateinit var userFb: UsersFb


class ProfileFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //firebase initialization
        auth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().getReference("user")
        user = auth.currentUser!!
        val userId = user.uid.toString()
        Log.d(TAG, "uid: $userId")
        userFb = UsersFb()

        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        val profilePic = view.findViewById<ImageView>(R.id.profile_pic)
        val logoutButton = view.findViewById<Button>(R.id.logout)

        if (userId.isNotEmpty()) {
            getUserData()
        }
        //logout button
        logoutButton.setOnClickListener {
            val loginIntent = Intent(context, Login::class.java)
            auth.signOut()
            startActivity(loginIntent)
            activity?.finish()
        }

        return view
    }

    //loads userdata from firebase and sets it to the profile
    private fun getUserData() {
        databaseReference.child(user.uid).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                userFb = dataSnapshot.getValue(UsersFb::class.java)!!
                //userName?.text   = userFb.username
                Log.d(TAG, "Name: ${userFb.username}")
                val username = view?.findViewById<TextView>(R.id.profile_username)
                username?.text = userFb.username

            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }
}