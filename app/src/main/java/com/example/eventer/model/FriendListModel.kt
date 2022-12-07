package com.example.eventer.model

class FriendListModel {

    var friendId : String? = null
    var name : String? = null

    constructor()
    constructor(senderId: String?, name: String?) {
        this.friendId = senderId
        this.name = name
    }

}