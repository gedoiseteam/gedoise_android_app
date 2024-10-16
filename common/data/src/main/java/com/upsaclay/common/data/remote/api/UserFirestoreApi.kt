package com.upsaclay.common.data.remote.api

import com.upsaclay.common.data.remote.UserFirestoreModel

internal interface UserFirestoreApi {
    suspend fun getUser(userId: Int): UserFirestoreModel?

    suspend fun getUser(userEmail: String): UserFirestoreModel?

    suspend fun getAllUsers(): List<UserFirestoreModel>

    suspend fun getOnlineUsers(): List<UserFirestoreModel>

    suspend fun createUser(userFirestoreModel: UserFirestoreModel): Result<Unit>

    suspend fun updateProfilePictureUrl(userId: String, profilePictureUrl: String?): Result<Unit>

    suspend fun isUserExist(email: String): Boolean
}