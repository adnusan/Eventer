package com.example.eventer.Adapter

import android.provider.ContactsContract.CommonDataKinds.Email

data class UsersFb(var username: String, var uid: String) {
    constructor() : this("", "")
}
