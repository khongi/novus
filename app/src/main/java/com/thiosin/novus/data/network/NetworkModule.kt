package com.thiosin.novus.data.network

import com.kirkbushman.auth.RedditAuth
import com.kirkbushman.auth.managers.StorageManager
import com.squareup.moshi.Moshi
import com.thiosin.novus.BuildConfig
import com.thiosin.novus.data.auth.AuthInfoProvider
import com.thiosin.novus.data.auth.AuthInterceptor
import com.thiosin.novus.data.network.json.CResponseDataChildListAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Qualifier

@Module
@InstallIn(ActivityComponent::class)
class NetworkModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class UserlessAuth

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class InstalledAppAuth

    @Provides
    fun provideStorageManager(authInfoProvider: AuthInfoProvider): StorageManager {
        return authInfoProvider
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

    @InstalledAppAuth
    @Provides
    fun provideUserRedditAuth(storageManager: StorageManager): RedditAuth {
        return RedditAuth.Builder()
            .setApplicationCredentials(BuildConfig.CLIENT_ID, BuildConfig.REDIRECT_URL)
            .setScopes(arrayOf("read", "identity", "mysubreddits", "vote"))
            .setStorageManager(storageManager)
            .setLogging(true)
            .build()
    }

    @Provides
    @ActivityScoped
    fun provideOkHttpClient(
        authInfoProvider: AuthInfoProvider,
        @UserlessAuth userlessAuth: RedditAuth,
        @InstalledAppAuth installedAppAuth: RedditAuth,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addNetworkInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.HEADERS
                }
            )
            .addInterceptor(AuthInterceptor(
                authInfoProvider = authInfoProvider,
                userlessAuth = userlessAuth,
                installedAppAuth = installedAppAuth
            ))
            .build()
    }

    @Provides
    @ActivityScoped
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val innerMoshi = Moshi.Builder().build()
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(
                Moshi.Builder()
                    .add(CResponseDataChildListAdapter(innerMoshi))
                    .build()
            ))
            .build()
    }

    @Provides
    @ActivityScoped
    fun provideRedditApi(retrofit: Retrofit): RedditAPI = retrofit.create(RedditAPI::class.java)

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class PageSize

    @PageSize
    @Provides
    fun providePageSize(): Int = 25
}