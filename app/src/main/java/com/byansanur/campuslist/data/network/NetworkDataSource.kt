package com.byansanur.campuslist.data.network

import com.byansanur.campuslist.data.entity.CampusModel
import com.byansanur.campuslist.data.network.api.ApiServiceImpl
import com.byansanur.campuslist.data.network.response.toData
import com.byansanur.campuslist.utils.Utils.MY_COUNTRY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkDataSource @Inject constructor(
    private val api: ApiServiceImpl
) {

    suspend fun getCampusList(): Flow<List<CampusModel>> = withContext(Dispatchers.IO) {
        api.getCampus(
            country = MY_COUNTRY,
            limit = 10,
            offset = 0
        ).map {
            it.data?.map { data -> data.toData() } ?: emptyList()
        }
    }

    suspend fun getSearchCampus(search: String) : Flow<List<CampusModel>> = withContext(Dispatchers.IO) {
        api.getSearchCampus(
            name = search,
            country = MY_COUNTRY,
            limit = 10,
            offset = 0
        ).map {
            it.data?.map { data -> data.toData() } ?: emptyList()
        }
    }

}