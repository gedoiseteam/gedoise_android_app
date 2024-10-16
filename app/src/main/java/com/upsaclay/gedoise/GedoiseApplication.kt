package com.upsaclay.gedoise

import android.app.Application
import com.upsaclay.authentication.authenticationModule
import com.upsaclay.authentication.data.authenticationDataModule
import com.upsaclay.common.commonModule
import com.upsaclay.common.data.commonDataModule
import com.upsaclay.message.data.messageDataModule
import com.upsaclay.message.messageModule
import com.upsaclay.news.data.newsDataModule
import com.upsaclay.news.newsModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber
import timber.log.Timber.Forest.plant

class GedoiseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@GedoiseApplication)
            modules(
                listOf(
                    appModule,
                    authenticationModule,
                    authenticationDataModule,
                    commonModule,
                    commonDataModule,
                    newsModule,
                    newsDataModule,
                    messageModule,
                    messageDataModule
                )
            )
        }
        plant(Timber.DebugTree())
    }
}