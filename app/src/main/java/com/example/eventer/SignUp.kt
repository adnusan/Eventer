package com.example.eventer

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


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
        val signupName = findViewById<TextView>(R.id.signup_name)

        val signupButton = findViewById<Button>(R.id.signup_button)
        val loginRedirect = findViewById<Button>(R.id.login_redirect_button)
        val uid = auth.currentUser?.uid



        signupButton.setOnClickListener {
            createAccount(
                signupUsername.text.toString(),
                signupPassword.text.toString(),
                signupName.text.toString(),
                uid.toString()
            )

            Toast.makeText(
                this,
                "Signup Successful for ${signupUsername.text.toString()}",
                Toast.LENGTH_SHORT
            ).show()

        }

        loginRedirect.setOnClickListener {
            val logRedirect = Intent(applicationContext, Login::class.java)
            startActivity(logRedirect)
        }

    }

    //function to create account, stores name, username, password, and uid in firebase
    private fun createAccount(email: String, password: String, name: String, uid: String) {
        val i = Intent(applicationContext, Login::class.java)

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    val userid = user?.uid
                    //create haspmap for user
                    val userMap = HashMap<String, Any>()
                    //userMap["uid"] = userid.toString()
                    userMap["email"] = email
                    userMap["password"] = password
                    userMap["username"] = name
                    userMap["uid"] = userid.toString()
                    if (userid != null) {
                        database.child("user").child(userid).setValue(userMap)
                    }
                    startActivity(i)
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }
    }

}