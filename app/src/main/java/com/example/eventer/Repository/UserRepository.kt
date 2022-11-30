package com.example.eventer.Repository

import androidx.lifecycle.MutableLiveData
import com.example.eventer.model.UsersFb
import com.google.firebase.database.*

//class to get data from firebase
class UserRepository {
    private val database: DatabaseReference = FirebaseDatabase.getInstance().getReference("user")

    @Volatile
    private var INSTANCE: UserRepository? = null

    //make sure there is only one instance
    fun getInstance(): UserRepository {
        return INSTANCE ?: synchronized(this) {
            val inst = UserRepository()
            INSTANCE = inst
            inst
        }

    }


    fun loadUsers(userList: MutableLiveData<List<UsersFb>>) {

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    val updatedUserList: List<UsersFb> =
                        snapshot.children.mapNotNull { dataSnapshot ->
                            dataSnapshot.getValue(UsersFb::class.java)
                        }

                    userList.postValue(updatedUserList)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                //do nothing
            }
        })


    }


}