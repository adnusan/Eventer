package com.example.eventer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlin.math.sign

class SignUp : AppCompatActivity() {


    private lateinit var database: DatabaseReference
    private fun initializeDbRef() {
        // [START initialize_database_ref]
        database = Firebase.database.reference


        // [END initialize_database_ref]
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        initializeDbRef()

        val signupUsername = findViewById<TextView>(R.id.signup_username)
        val signupPassword = findViewById<TextView>(R.id.signup_username)

        val signupButton = findViewById<Button>(R.id.signup_button)
        val loginRedirect = findViewById<Button>(R.id.login_redirect_button)

        signupButton.setOnClickListener{
            database.child("users").child(signupUsername.text.toString()).setValue(signupPassword.text.toString())
            Toast.makeText(this, "Username: ${signupUsername.text} Password: ${signupPassword.text}", Toast.LENGTH_SHORT).show()
        }

        loginRedirect.setOnClickListener{
            val logRedirect = Intent(applicationContext, MainActivity::class.java)
            startActivity(logRedirect)
        }

    }
}