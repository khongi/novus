package com.thiosin.novus.data.network

import android.content.Context
import com.kirkbushman.auth.RedditAuth
import com.kirkbushman.auth.managers.SharedPrefsStorageManager
import com.kirkbushman.auth.managers.StorageManager
import com.thiosin.novus.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ActivityComponent::class)
class NetworkModule {

    @Provides
    fun provideSharedPrefsStorageManager(@ApplicationContext context: Context): StorageManager {
        return SharedPrefsStorageManager(context)
    }

    @Provides
    fun provideRedditAuth(storageManager: StorageManager): RedditAuth {
        return RedditAuth.Builder()
            // specify the credentials you can find on your reddit app console,
            // in this case only the client id is provided.
            .setUserlessCredentials(BuildConfig.CLIENT_ID)
            // the api enpoints scopes this client will need
            .setScopes("*")
            // to manage tokens info in memory
            .setStorageManager(storageManager)
            // if you set this flag to 'true' it will add to the OkHttp Client a listener to log the
            // Request and Response object, to make it easy to debug.
            .setLogging(true)
            .build()
    }
}