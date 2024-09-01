package com.upsaclay.message.data.remote

import com.upsaclay.common.data.model.ServerResponse.EmptyResponse
import com.upsaclay.common.data.model.ServerResponse.IntResponse
import com.upsaclay.common.data.model.UserDTO

import com.upsaclay.common.domain.model.User
import com.upsaclay.common.utils.e
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

internal class UserRemoteDataSource(private val userRemoteApi: com.upsaclay.message.data.remote.api.UserRemoteApi) {
}