package com.example.eventer

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth

class Login : AppCompatActivity() {


    private lateinit var database: DatabaseReference //database ref
    private lateinit var auth: FirebaseAuth //firebase auth

    //initialize firebase db
    private fun initializeDbRef() {
        database = Firebase.database.reference
    }
    private fun initializeAuth() {
        auth = Firebase.auth
    }

    override fun onStart() {
        super.onStart()
        val firebaseUser = FirebaseAuth.getInstance().currentUser

        //checking if user is logged in
        if (firebaseUser != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initializeDbRef()
        initializeAuth()

        val login = findViewById<TextView>(R.id.login)
        val password = findViewById<TextView>(R.id.password)
        val loginButton = findViewById<Button>(R.id.loginButton)
        val signupLink = findViewById<TextView>(R.id.signup_link)




        //check user login and load profile
        loginButton.setOnClickListener {

            if(login.text.toString().isNotBlank() && password.text.toString().isNotBlank()){
                signIn(login.text.toString(), password.text.toString())
                Toast.makeText(this, "Login: ${login.text} Password: ${password.text}", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this, "Please enter a username and password", Toast.LENGTH_SHORT).show()
            }

        }

        signupLink.setOnClickListener {
            //TODO: Add intent to go to signup page
            Toast.makeText(this, "Sign Up", Toast.LENGTH_SHORT).show()
            val i = Intent(applicationContext, SignUp::class.java)
            startActivity(i)
        }
}

    private fun signIn(email: String, password: String) {
        // [START sign_in_with_email]
        val loadProfile = Intent(this, MainActivity::class.java)

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    startActivity(loadProfile)
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
        // [END sign_in_with_email]
    }
}
