package com.byansanur.campuslist.data.network.api

import com.byansanur.campuslist.data.network.response.CampusResponse
import kotlinx.coroutines.flow.Flow

interface CampusApi {

    fun getCampus(country:String="indonesia", limit: Int = 10, offset: Int = 0) :
            Flow<ApiResult<List<CampusResponse>>>

    fun getSearchCampus(country:String="indonesia", search: String, limit: Int = 10, offset: Int = 0) :
            Flow<ApiResult<List<CampusResponse>>>
}