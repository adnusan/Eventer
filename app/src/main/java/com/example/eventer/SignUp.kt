package com.example.eventer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlin.math.sign

class SignUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val signupUsername = findViewById<TextView>(R.id.signup_username)
        val signupPassword = findViewById<TextView>(R.id.signup_username)

        val signupButton = findViewById<Button>(R.id.signup_button)
        val loginRedirect = findViewById<Button>(R.id.login_redirect_button)

        signupButton.setOnClickListener{
            Toast.makeText(this, "Username: ${signupUsername.text} Password: ${signupPassword.text}", Toast.LENGTH_SHORT).show()
        }

        loginRedirect.setOnClickListener{
            val logRedirect = Intent(applicationContext, MainActivity::class.java)
            startActivity(logRedirect)
        }

    }
}