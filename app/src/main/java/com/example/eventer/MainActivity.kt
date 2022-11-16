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
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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