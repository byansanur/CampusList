package com.byansanur.campuslist.data.network.api

import com.byansanur.campuslist.data.network.response.CampusResponse
import com.byansanur.campuslist.utils.Utils.BASE_URL
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ResponseException
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import javax.inject.Inject

class ApiServiceImpl @Inject constructor(private val httpClient: HttpClient) : CampusApi {

    private suspend inline fun <reified T> safeApiCall(
        endpoint: String, parameters: Map<String, Any> = emptyMap()
    ): ApiResult<List<T>> = try {
        val responseBody = httpClient.get(endpoint) {
            parameters.forEach { (key, value) -> parameter(key, value) }
        }.body<JsonArray>() // Now directly deserialize to JsonArray

        val data = responseBody.map { Json.decodeFromString<T>(it.toString()) }
        ApiResult.Success(data)
    } catch (e: Exception) {
        e.printStackTrace()
        if (e is ResponseException) {
            val errorText = e.response.bodyAsText()
            println("Response Body: $errorText")
        }
        ApiResult.Error(e.message ?: "Something went wrong")
    }

    override fun getCampus(
        country: String,
        limit: Int,
        offset: Int
    ): Flow<ApiResult<List<CampusResponse>>> = flow {
        emit(ApiResult.Loading())
        emit(
            safeApiCall(
                endpoint = "search",
                mapOf("country" to country, "limit" to limit, "offset" to offset)
            )
        )
    }

    override fun getSearchCampus(
        country: String,
        name: String,
        limit: Int,
        offset: Int
    ): Flow<ApiResult<List<CampusResponse>>> = flow {
        emit(ApiResult.Loading())
        emit(safeApiCall(
            endpoint = "search",
            mapOf("country" to country, "name" to name, "limit" to limit, "offset" to offset)
        ))
    }
}