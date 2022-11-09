package com.example.eventer

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    fun initializeDbRef() {
        // [START initialize_database_ref]
        database = Firebase.database.reference
        database.child("user").setValue("nuu")

        // [END initialize_database_ref]
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val login = findViewById<TextView>(R.id.login)
        val password = findViewById<TextView>(R.id.password)
        val loginButton = findViewById<Button>(R.id.loginButton)
        val signupLink = findViewById<TextView>(R.id.signup_link)

        loginButton.setOnClickListener {
            Toast.makeText(this, "Login: ${login.text} Password: ${password.text}", Toast.LENGTH_SHORT).show()
            initializeDbRef()
        }

        signupLink.setOnClickListener {
            //TODO: Add intent to go to signup page
            Toast.makeText(this, "Sign Up", Toast.LENGTH_SHORT).show()
            val i = Intent(applicationContext, SignUp::class.java)
            startActivity(i)

        }
}
}
