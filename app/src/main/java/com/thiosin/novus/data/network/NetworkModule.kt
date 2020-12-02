package com.thiosin.novus.data.network

import android.content.Context
import com.kirkbushman.araw.helpers.AuthUserlessHelper
import com.kirkbushman.auth.RedditAuth
import com.kirkbushman.auth.managers.SharedPrefsStorageManager
import com.kirkbushman.auth.managers.StorageManager
import com.thiosin.novus.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Qualifier

@Module
@InstallIn(ActivityComponent::class)
class NetworkModule {

    @Provides
    fun provideAuthUserlessHelper(@ApplicationContext context: Context): AuthUserlessHelper {
        return AuthUserlessHelper(
            context = context,
            clientId = BuildConfig.CLIENT_ID,
            deviceId = null,
            scopes = arrayOf(),
            logging = true
        )
    }

    @Provides
    fun provideSharedPrefsStorageManager(@ApplicationContext context: Context): StorageManager {
        return SharedPrefsStorageManager(context)
    }

    @UserlessAuth
    @Provides
    fun provideUserlessRedditAuth(storageManager: StorageManager): RedditAuth {
        return RedditAuth.Builder()
            .setUserlessCredentials(BuildConfig.CLIENT_ID)
            .setScopes("*")
            .setStorageManager(storageManager)
            .setLogging(true)
            .build()
    }

    @UserAuth
    @Provides
    fun provideUserRedditAuth(storageManager: StorageManager): RedditAuth {
        return RedditAuth.Builder()
            .setApplicationCredentials(BuildConfig.CLIENT_ID, BuildConfig.REDIRECT_URL)
            // TODO define scopes
            .setScopes(arrayOf("read"))
            .setStorageManager(storageManager)
            .setLogging(true)
            .build()
    }

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class UserlessAuth

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class UserAuth
}