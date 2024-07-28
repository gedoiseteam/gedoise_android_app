package com.upsaclay.core.data.model

import com.google.gson.annotations.SerializedName

data class UserDTO(
    @SerializedName("USER_ID")
    val userId: Int? = null,
    @SerializedName("USER_FIRST_NAME")
    val userFirstName: String,
    @SerializedName("USER_LAST_NAME")
    val userLastName: String,
    @SerializedName("USER_EMAIL")
    val userEmail: String,
    @SerializedName("USER_SCHOOL_LEVEL")
    val userSchoolLevel: String,
    @SerializedName("USER_IS_MEMBER")
    val userIsMember: Int = 0
)
