package com.upsaclay.authentication.domain.repository

import kotlinx.coroutines.flow.Flow


interface AuthenticationRepository {
    val isAuthenticated: Flow<Boolean>

    suspend fun login(email: String, password: String, hash: String): Result<Unit>

    suspend fun logout()
}