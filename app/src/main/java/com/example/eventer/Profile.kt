package com.example.eventer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

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
        val currentUser = auth.currentUser
        val currentUsername = currentUser?.email

        val name = findViewById<TextView>(R.id.profile_username)

        //update username with username from database
        name.text =  currentUsername
        Toast.makeText(baseContext, "Username: $currentUsername", Toast.LENGTH_SHORT).show()

    }
}