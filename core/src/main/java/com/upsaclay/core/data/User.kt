package com.upsaclay.core.data

import com.google.gson.annotations.SerializedName

data class User(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val email: String
){
    val fullname = firstName[0].uppercaseChar() + " " + lastName[0].uppercaseChar()
}

data class UserDTO(
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("user_first_name")
    val userFirstName: String,
    @SerializedName("user_last_name")
    val userLastName: String,
    @SerializedName("user_email")
    val userEmail: String
)