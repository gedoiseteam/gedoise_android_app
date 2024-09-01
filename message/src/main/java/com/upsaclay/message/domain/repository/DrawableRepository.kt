package com.upsaclay.message.domain.repository

import android.net.Uri

interface DrawableRepository {
    fun getDrawableUri(drawableId: Int): Uri?
}