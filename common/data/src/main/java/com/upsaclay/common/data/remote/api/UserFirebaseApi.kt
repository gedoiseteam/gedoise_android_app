package com.upsaclay.common.data.remote.api

import kotlinx.coroutines.flow.Flow

internal interface UserFirebaseApi {
    suspend fun getUser(userId: String): RemoteUserFirebase?

    suspend fun getAllUsers(): Flow<List<RemoteUserFirebase>>

    suspend fun createUser(remoteUserFirebase: RemoteUserFirebase): Result<Unit>

    suspend fun updateProfilePictureUrl(userId: String, profilePictureUrl: String?): Result<Unit>
}