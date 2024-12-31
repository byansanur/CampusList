package com.byansanur.campuslist.data.network.di

import com.byansanur.campuslist.data.network.api.ApiServiceImpl
import com.byansanur.campuslist.data.network.api.CampusApi
import com.byansanur.campuslist.utils.Utils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {
    @Singleton
    @Provides
    fun provideHttpClient():HttpClient{
        return HttpClient(Android){
            install(Logging){
                level = LogLevel.ALL
            }
            install(DefaultRequest){
                url(Utils.BASE_URL)
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }
            install(ContentNegotiation){
                json(Json)
            }
        }
    }

    @Singleton
    @Provides
    fun provideApiService(httpClient: HttpClient): CampusApi = ApiServiceImpl(httpClient)

    @Provides
    fun provideDispatcher():CoroutineDispatcher = Dispatchers.Default
}