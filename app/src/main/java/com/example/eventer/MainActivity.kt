package com.example.eventer

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import com.example.eventer.Fragments.ChatFragment
import com.example.eventer.Fragments.MapsFragment
import com.example.eventer.Fragments.ProfileFragment
import com.example.eventer.databinding.ActivityMainBinding
import com.example.eventer.model.UsersFb
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


private lateinit var auth: FirebaseAuth
private lateinit var databaseReference: DatabaseReference
private lateinit var user: FirebaseUser

class MainActivity : AppCompatActivity() {
    //view binding
    private lateinit var binding: ActivityMainBinding
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient


    companion object {
        private const val PERMISSION_REQUEST_CODE = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)
        replaceFragment(ChatFragment())


        //firebase initialization
        auth = FirebaseAuth.getInstance()
        databaseReference = Firebase.database.reference
        user = auth.currentUser!!
        databaseReference = Firebase.database.reference.child("user").child(user.uid)


        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val user: UsersFb? = snapshot.getValue(UsersFb::class.java)
                    Toast.makeText(
                        this@MainActivity,
                        "Welcome ${user?.username}",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.d("MainActivity", "User: ${user?.email}")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("MainActivity", "Error: ${error.message}")
            }
        })


        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.nav_bar)
        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.chat_nav -> {
                    val fragment = ChatFragment()
                    replaceFragment(fragment)
                    Toast.makeText(this, "Chat Nav", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.event_nav -> {
                    Log.d("MainActivity", "Event Nav")
                    val fragment = MapsFragment()
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
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }

//    private fun checkPermission(): Boolean {
//        return ActivityCompat.checkSelfPermission(
//            this,
//            Manifest.permission.ACCESS_FINE_LOCATION
//        ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//            this,
//            Manifest.permission.ACCESS_COARSE_LOCATION
//        ) == PackageManager.PERMISSION_GRANTED
//
//    }
//
//    private fun requestPermission(){
//        ActivityCompat.requestPermissions(this,
//            arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION), PERMISSION_REQUEST_CODE)
//
//    }
//
//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        when(requestCode){
//            PERMISSION_REQUEST_CODE -> if(grantResults.isNotEmpty()){
//                val locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED
//                val coarseLocationAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED
//                if(locationAccepted && coarseLocationAccepted){
//                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
//                }else{
//                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
//                    if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){
//                        Toast.makeText(this, "You should enable this permission", Toast.LENGTH_SHORT).show()
//                    }else{
//                        Toast.makeText(this, "You should enable this permission from settings", Toast.LENGTH_SHORT).show()
//                    }
//                }
//            }
//        }
//    }






}