package com.ldh.starwars_mvvm_clone

import android.app.Application
import com.ldh.starwars_mvvm_clone.di.networkModule
import com.ldh.starwars_mvvm_clone.di.remoteDataSourceModule
import com.ldh.starwars_mvvm_clone.di.useCasesModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

internal class StarwarsApplication: Application() {

    // Application Level 에서의 onCreate()임 [타고 들어가보자]
    override fun onCreate() {
        super.onCreate()

        startKoin {
            // add android context
            androidContext(this@StarwarsApplication)
            modules(
                networkModule,
                remoteDataSourceModule,
                useCasesModule
            )
        }

    }
}