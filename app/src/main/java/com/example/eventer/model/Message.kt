package com.example.eventer.model

class Message {
    var message: String? = null
    var senderId: String? = null
    var receiverId: String? = null

    constructor()
    constructor(message: String?, senderId: String?, receiverId: String?) {
        this.message = message
        this.senderId = senderId
        this.receiverId = receiverId
    }
}