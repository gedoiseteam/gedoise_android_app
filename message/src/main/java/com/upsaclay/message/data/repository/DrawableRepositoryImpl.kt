package com.upsaclay.message.data.repository

import android.content.Context
import android.net.Uri
import com.upsaclay.common.domain.repository.DrawableRepository

class DrawableRepositoryImpl(private val context: Context): DrawableRepository {
    override fun getDrawableUri(drawableId: Int): Uri? {
        return Uri.parse("android.resource://${context.packageName}/$drawableId")
    }
}