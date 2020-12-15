package com.thiosin.novus.data.network.util

import com.squareup.moshi.JsonDataException
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import java.net.SocketTimeoutException

suspend fun <T> apiCallNullable(block: suspend () -> T?): T? {
    return try {
        block()
    } catch (e: IOException) {
        Timber.e(e)
        null
    } catch (e: HttpException) {
        Timber.e(e)
        null
    } catch (e: SocketTimeoutException) {
        Timber.e(e)
        null
    } catch (e: JsonDataException) {
        Timber.e(e)
        null
    } catch (e: JsonDataException) {
        Timber.e(e)
        null
    }
}

suspend fun apiCallBoolean(block: suspend () -> Unit): Boolean {
    return try {
        block()
        true
    } catch (e: IOException) {
        Timber.e(e)
        false
    } catch (e: HttpException) {
        Timber.e(e)
        false
    } catch (e: SocketTimeoutException) {
        Timber.e(e)
        false
    } catch (e: JsonDataException) {
        Timber.e(e)
        false
    }
}

suspend fun <T> apiCallList(block: suspend () -> List<T>): List<T> {
    return try {
        block()
    } catch (e: IOException) {
        Timber.e(e)
        emptyList()
    } catch (e: HttpException) {
        Timber.e(e)
        emptyList()
    } catch (e: SocketTimeoutException) {
        Timber.e(e)
        emptyList()
    } catch (e: JsonDataException) {
        Timber.e(e)
        emptyList()
    }
}
