package com.example.eventer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val login = findViewById<TextView>(R.id.login)
        val password = findViewById<TextView>(R.id.password)
        val loginButton = findViewById<Button>(R.id.loginButton)
        val signup_link = findViewById<TextView>(R.id.signup_link)

        loginButton.setOnClickListener {
            Toast.makeText(this, "Login: ${login.text} Password: ${password.text}", Toast.LENGTH_SHORT).show()

        }

        signup_link.setOnClickListener() {
            //TODO: Add intent to go to signup page
            Toast.makeText(this, "Sign Up", Toast.LENGTH_SHORT).show()

    }
}
}
