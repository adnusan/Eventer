package com.example.eventer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.database.Query
import com.example.eventer.Users
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener


class Profile : AppCompatActivity() {
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
        setContentView(R.layout.activity_profile)
        initializeAuth()
        initializeDbRef()

        //val firebaseUser = FirebaseAuth.getInstance().currentUser
        //val myref = database.child("users").child(firebaseUser!!.uid)

        val currentUser = auth.currentUser
        val currentEmail = currentUser?.email //curent user email

        val name = findViewById<TextView>(R.id.profile_username)
        val logoutButton = findViewById<Button>(R.id.logout)

        val j = currentEmail?.indexOf("@")
        val username = currentEmail?.substring(0, j!!)

        name.text= username



        Toast.makeText(baseContext, "Username: $username", Toast.LENGTH_SHORT).show()

        logoutButton.setOnClickListener {
            auth.signOut()
            val intent = Intent(this, Login::class.java)
            Toast.makeText(baseContext, "Logging out", Toast.LENGTH_SHORT).show()

            startActivity(intent)
            finish()
        }

    }
}