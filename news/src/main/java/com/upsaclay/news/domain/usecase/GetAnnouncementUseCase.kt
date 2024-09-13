package com.upsaclay.news.domain.usecase

import com.upsaclay.news.domain.model.Announcement
import com.upsaclay.news.domain.repository.AnnouncementRepository

class GetAnnouncementUseCase(
    private val announcementRepository: AnnouncementRepository
) {
    suspend operator fun invoke(id: Int): Announcement? {
        return announcementRepository.getAnnouncement(id)
    }
}