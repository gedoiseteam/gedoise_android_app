package com.upsaclay.common.data.remote.api

import com.upsaclay.common.domain.model.ServerResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

internal interface ImageApi {
    @Multipart
    @POST("image/upload")
    suspend fun uploadImage(@Part image: MultipartBody.Part): Response<com.upsaclay.common.domain.model.ServerResponse.EmptyResponse>

    @DELETE("image/{filename}")
    suspend fun deleteImage(@Path("filename") filename: String): Response<com.upsaclay.common.domain.model.ServerResponse.EmptyResponse>
}