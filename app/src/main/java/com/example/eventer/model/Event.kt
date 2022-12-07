package com.example.eventer.model

class Event {
    var username: String? = null
    var title: String? = null
    var longitude: Double? = null
    var latitude: Double? = null

    constructor()
    constructor(username: String?, title: String?, longitude: Double, latitude: Double) {
        this.username = username
        this.title = title
        this.longitude = longitude
        this.latitude = latitude
    }
}