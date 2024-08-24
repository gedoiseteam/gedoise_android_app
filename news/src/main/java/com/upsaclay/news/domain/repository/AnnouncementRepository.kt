package com.upsaclay.news.domain.repository

import com.upsaclay.news.domain.model.Announcement
import kotlinx.coroutines.flow.Flow

interface AnnouncementRepository {
    val announcements: Flow<List<Announcement>>

    suspend fun refreshAnnouncements()

    suspend fun createAnnouncement(announcement: Announcement): Result<Int>

    suspend fun deleteAnnouncement(announcement: Announcement): Result<Unit>

    suspend fun updateAnnouncement(announcement: Announcement): Result<Unit>
}