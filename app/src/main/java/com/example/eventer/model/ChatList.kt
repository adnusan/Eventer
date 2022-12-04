package com.example.eventer.model

class ChatList {
    var senderId : String? = null
    var name : String? = null

    constructor()
    constructor(senderId: String?, name: String?) {
        this.senderId = senderId
        this.name = name
    }

}