package com.thiosin.novus.data.network

import android.content.Context
import androidx.paging.PagingConfig
import com.kirkbushman.araw.RedditClient
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
import dagger.hilt.android.scopes.ActivityScoped
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class)
class NetworkModule {

    @Provides
    fun provideSharedPrefsStorageManager(@ApplicationContext context: Context): StorageManager {
        return SharedPrefsStorageManager(context)
    }

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class UserlessAuth

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

    @UserlessAuth
    @Provides
    fun provideUserlessRedditAuth(storageManager: StorageManager): RedditAuth {
        return RedditAuth.Builder()
            .setUserlessCredentials(BuildConfig.CLIENT_ID)
            .setScopes("*")
            .setStorageManager(storageManager)
            .setLogging(false)
            .build()
    }

    @UserlessAuth
    @Provides
    fun provideUserlessRedditClient(authUserlessHelper: AuthUserlessHelper): RedditClient {
        return authUserlessHelper.getRedditClient() ?: throw IllegalStateException("No token")
    }

    @UserAuth
    @Provides
    fun provideUserRedditAuth(storageManager: StorageManager): RedditAuth {
        return RedditAuth.Builder()
            .setApplicationCredentials(BuildConfig.CLIENT_ID, BuildConfig.REDIRECT_URL)
            // TODO define scopes
            .setScopes(arrayOf("read"))
            .setStorageManager(storageManager)
            .setLogging(false)
            .build()
    }

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class UserAuth

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class PageSize

    @PageSize
    @Provides
    fun providePageSize(): Int = 25

    @Provides
    fun providePagerConfig(@PageSize pageSize: Int): PagingConfig {
        return PagingConfig(
            pageSize = pageSize,
            prefetchDistance = 15,
            initialLoadSize = pageSize
        )
    }

    @UserlessAuth
    @Provides
    @ActivityScoped
    fun provideOkHttpClient(@UserlessAuth redditAuth: RedditAuth): OkHttpClient {
        return OkHttpClient.Builder()
            .addNetworkInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .addInterceptor(AuthInterceptor(redditAuth))
            .build()
    }

    @UserlessAuth
    @Provides
    @ActivityScoped
    fun provideRetrofit(@UserlessAuth okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    @UserlessAuth
    @Provides
    @ActivityScoped
    fun provideRedditApi(@UserlessAuth retrofit: Retrofit): RedditAPI = retrofit.create(RedditAPI::class.java)

}