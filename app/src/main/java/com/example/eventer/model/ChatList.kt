package com.example.eventer.model

class ChatList {
    var initial: String = ""
    var username: String = ""
    var uid: String = ""

    constructor()

    constructor(id: String, username: String) {
        this.uid = id
        this.username = username
        this.initial = id.substring(0, 1)

    }
}