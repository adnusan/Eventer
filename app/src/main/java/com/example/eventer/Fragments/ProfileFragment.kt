package com.example.eventer.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.eventer.Login
import com.example.eventer.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    //firebase
    private lateinit var auth: FirebaseAuth
    //database reference
    private lateinit var database: DatabaseReference


    private fun initializeDbRef() {
        database = Firebase.database.reference
    }

    //Firebase auth
    private fun initializeAuth() {
        auth = Firebase.auth
    }





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        initializeAuth()
        initializeDbRef()


        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        val profilePic = view.findViewById<ImageView>(R.id.profile_pic)
        val usernameView = view.findViewById<TextView>(R.id.profile_username)
        val logoutButton = view.findViewById<Button>(R.id.logout)

        val currentUser = auth.currentUser
        val ref = FirebaseDatabase.getInstance().getReference("users").child(currentUser!!.uid)

        val currentEmail = currentUser.email //curent user email
        val j = currentEmail?.indexOf("@")
        val username = currentEmail?.substring(0, j!!)
        // Inflate the layout for this fragment
        usernameView.text = username

        val friend_button = view.findViewById<Button>(R.id.friend_button)
        friend_button.setOnClickListener {
            val friendFragment = UserFragment()
            replaceFragment(friendFragment)
        }

        logoutButton.setOnClickListener {
            val loginIntent = Intent(context, Login::class.java)
            auth.signOut()
            startActivity(loginIntent)
            activity?.finish()
        }


        return view
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    private fun replaceFragment(fragment: Fragment) {
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.frame_layout, fragment)
        transaction?.disallowAddToBackStack()
        transaction?.commit()

    }
}