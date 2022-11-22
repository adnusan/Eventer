package com.example.eventer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.eventer.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.example.eventer.database.FirebaseClass
import com.example.eventer.model.UsersFb


class Profile : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    //firebase
    private val firebaseClass = FirebaseClass()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        //initialize firebase
        firebaseClass.auth = Firebase.auth
        firebaseClass.database = Firebase.database.reference
        firebaseClass.firebaseUser = firebaseClass.auth.currentUser!!
        firebaseClass.database = Firebase.database.reference.child("Users").child(firebaseClass.firebaseUser.uid)

        firebaseClass.database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val user: UsersFb? = snapshot.getValue(UsersFb::class.java)
                    Log.d("MainActivity", "User: ${user?.email}")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("MainActivity", "Error: ${error.message}")
            }
        })

        val currentUsername = firebaseClass.firebaseUser.displayName
        //text view to display username
        val name = findViewById<TextView>(R.id.profile_username)
        //logout button
        val logoutButton = findViewById<Button>(R.id.logout)
        name.text= currentUsername


        Toast.makeText(baseContext, "Username: $currentUsername", Toast.LENGTH_SHORT).show()

        //logout button
        logoutButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, Login::class.java)
            Toast.makeText(baseContext, "Logging out", Toast.LENGTH_SHORT).show()

            startActivity(intent)
            finish()
        }

    }
}