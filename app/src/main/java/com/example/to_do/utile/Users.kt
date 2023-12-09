package com.example.to_do.utile

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class Users(
    var id : String = UUID.randomUUID().toString(),
    var userType: String ? = null,
    var userId : String ?= null,
    var userName : String ?= null,
    var userEmail : String ?= null,
    var userPassword : String ?= null,
    var userImage : String ?= null


) : Parcelable
