package com.example.eventer

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlin.math.sign
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth


class SignUp : AppCompatActivity() {

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
        setContentView(R.layout.activity_sign_up)
        initializeDbRef()
        initializeAuth()

        val signupUsername = findViewById<TextView>(R.id.signup_username)
        val signupPassword = findViewById<TextView>(R.id.signup_password)

        val signupButton = findViewById<Button>(R.id.signup_button)
        val loginRedirect = findViewById<Button>(R.id.login_redirect_button)

        signupButton.setOnClickListener {
            //add username and password to database
           //atabase.child("user").child("username").setValue(signupUsername.text.toString())
           //atabase.child("user").child("password").setValue(signupPassword.text.toString())
            createAccount(signupUsername.text.toString(), signupPassword.text.toString())
            Toast.makeText(
                this,
                "Username: ${signupUsername.text} Password: ${signupPassword.text}",
                Toast.LENGTH_SHORT
            ).show()
        }

        loginRedirect.setOnClickListener {
            val logRedirect = Intent(applicationContext, MainActivity::class.java)
            startActivity(logRedirect)
        }

    }

    private fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    val userid = user?.uid;
                    //create haspmap for user
                    val userMap = HashMap<String, Any>()
                    userMap["uid"] = userid.toString()
                    userMap["email"] = email
                    userMap["password"] = password
                    if (userid != null) {
                        database.child("user").child(userid).setValue(userMap)
                    }
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()

                }
            }
    }

}