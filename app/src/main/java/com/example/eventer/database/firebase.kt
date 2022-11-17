package com.example.eventer.database

import com.example.eventer.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FirebaseClass {

    //firebase
    lateinit var auth: FirebaseAuth
    lateinit var firebaseUser: FirebaseUser


    //database reference
    lateinit var database: DatabaseReference


//    fun initializeDbRef()  {
//        val fb = FirebaseClass()
//        fb.database = Firebase.database.reference
//    }
//
//
//
//
////    fun getDatabaseRef() {
////        val fb = FirebaseClass()
////        //fb.getCurrentUser()
////        fb.database = Firebase.database.reference.child("Users").child(firebaseUser.uid)
////
////    }
//initialize firebase function
//fun initializeFirebase() {
//    val fb = FirebaseClass()
//    fb.auth = Firebase.auth
//    fb.firebaseUser = FirebaseClass().auth.currentUser!!
//    fb.database = Firebase.database.reference.child("Users").child(fb.firebaseUser.uid)
//}
}




