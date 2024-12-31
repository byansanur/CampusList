package com.byansanur.campuslist.data.network.api

import com.byansanur.campuslist.data.network.response.CampusResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ApiServiceImpl @Inject constructor(private val httpClient: HttpClient) : CampusApi {

    override fun getCampus(
        country: String,
        limit: Int,
        offset: Int
    ): Flow<ApiResult<List<CampusResponse>>> = flow {
        emit(ApiResult.Loading())
        try {
            emit(ApiResult.Success(httpClient.get() {
                parameter("country", country)
                parameter("limit", limit)
                parameter("offset", offset)
            }.body()))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(ApiResult.Error(e.message ?: "Something went wrong"))
        }
    }

    override fun getSearchCampus(
        country: String,
        search: String,
        limit: Int,
        offset: Int
    ): Flow<ApiResult<List<CampusResponse>>> = flow {
        emit(ApiResult.Loading())
        try {
            emit(ApiResult.Success(httpClient.get() {
                parameter("country", country)
                parameter("search", search)
                parameter("limit", limit)
                parameter("offset", offset)
            }.body()))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(ApiResult.Error(e.message ?: "Something went wrong"))
        }
    }
}