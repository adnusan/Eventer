package com.example.eventer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.eventer.Fragments.ChatFragment
import com.example.eventer.Fragments.EventFragment
import com.example.eventer.Fragments.ProfileFragment
import com.example.eventer.databinding.ActivityMainBinding
import com.example.eventer.model.Users
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.example.eventer.database.FirebaseClass

class MainActivity : AppCompatActivity() {
    //view binding
    private lateinit var binding: ActivityMainBinding
    //firebase
    private val firebaseClass = FirebaseClass()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //firebase initialization
        firebaseClass.auth = Firebase.auth
        firebaseClass.database = Firebase.database.reference
        firebaseClass.firebaseUser = firebaseClass.auth.currentUser!!

        firebaseClass.database = Firebase.database.reference.child("Users").child(firebaseClass.firebaseUser.uid)



        firebaseClass.database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val user: Users? = snapshot.getValue(Users::class.java)
                    Toast.makeText(this@MainActivity, "Welcome ${user?.email}", Toast.LENGTH_SHORT).show()
                    Log.d("MainActivity", "User: ${user?.email}")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("MainActivity", "Error: ${error.message}")
            }
        })




        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.nav_bar)
        bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.chat_nav -> {
                    val fragment = ChatFragment()
                    replaceFragment(fragment)
                    Toast.makeText(this, "Chat Nav", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.event_nav -> {
                    Log.d("MainActivity", "Event Nav")
                    val fragment = EventFragment()
                    replaceFragment(fragment)
                    Toast.makeText(this, "Event Nav", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.profile_nav -> {
                    val fragment = ProfileFragment()
                    replaceFragment(fragment)
                    Toast.makeText(this, "Profile Nav", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }


    }


    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()

    }
}