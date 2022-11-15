package com.example.eventer

class Users (){
    var email: String? = null
    var userid: String? = null
}

fun createUser(email: String, userid: String) {
    val user = Users()
    user.email = email
    user.userid = userid
}