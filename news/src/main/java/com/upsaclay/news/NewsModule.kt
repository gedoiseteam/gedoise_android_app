package com.upsaclay.news

import com.upsaclay.common.SERVER_1_RETROFIT_QUALIFIER
import com.upsaclay.news.data.local.AnnouncementLocalDataSource
import com.upsaclay.news.data.remote.AnnouncementRemoteDataSource
import com.upsaclay.news.data.remote.api.AnnouncementApi
import com.upsaclay.news.data.repository.AnnouncementRepositoryImpl
import com.upsaclay.news.domain.model.Announcement
import com.upsaclay.news.domain.repository.AnnouncementRepository
import com.upsaclay.news.domain.usecase.ConvertAnnouncementToJsonUseCase
import com.upsaclay.news.domain.usecase.CreateAnnouncementUseCase
import com.upsaclay.news.domain.usecase.DeleteAnnouncementUseCase
import com.upsaclay.news.domain.usecase.GetAllAnnouncementsUseCase
import com.upsaclay.news.domain.usecase.GetAnnouncementUseCase
import com.upsaclay.news.domain.usecase.GetOnlineUserUseCase
import com.upsaclay.news.domain.usecase.RefreshAnnouncementsUseCase
import com.upsaclay.news.domain.usecase.UpdateAnnouncementUseCase
import com.upsaclay.news.presentation.viewmodel.CreateAnnouncementViewModel
import com.upsaclay.news.presentation.viewmodel.EditAnnouncementViewModel
import com.upsaclay.news.presentation.viewmodel.NewsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val newsModule = module {
    single {
        get<Retrofit>(qualifier = named(SERVER_1_RETROFIT_QUALIFIER))
            .create(AnnouncementApi::class.java)
    }

    singleOf(::AnnouncementRepositoryImpl) { bind<AnnouncementRepository>() }
    singleOf(::AnnouncementRemoteDataSource)
    singleOf(::AnnouncementLocalDataSource)

    viewModelOf(::NewsViewModel)
    viewModel { (announcement: Announcement) ->
        EditAnnouncementViewModel(
            editedAnnouncement = announcement,
            updateAnnouncementUseCase =  get()
        )
    }
    viewModelOf(::CreateAnnouncementViewModel)

    singleOf(::ConvertAnnouncementToJsonUseCase)
    singleOf(::CreateAnnouncementUseCase)
    singleOf(::DeleteAnnouncementUseCase)
    singleOf(::GetAllAnnouncementsUseCase)
    singleOf(::GetAnnouncementUseCase)
    singleOf(::GetOnlineUserUseCase)
    singleOf(::RefreshAnnouncementsUseCase)
    singleOf(::UpdateAnnouncementUseCase)
}