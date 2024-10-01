package com.upsaclay.common.domain.model

import com.upsaclay.common.utils.capitalizeFirstLetter

data class User(
    val id: Int = -1,
    val firstName: String,
    val lastName: String,
    val email: String,
    val schoolLevel: String,
    val isMember: Boolean = false,
    val profilePictureUrl: String? = null
) {
    val fullName: String = "${firstName.capitalizeFirstLetter()} ${lastName.capitalizeFirstLetter()}"

    fun isUpdated(user: User): Boolean {
        return this.id == user.id &&
                this.profilePictureUrl != user.profilePictureUrl ||
                this.isMember != user.isMember ||
                this.schoolLevel != user.schoolLevel
    }
}
