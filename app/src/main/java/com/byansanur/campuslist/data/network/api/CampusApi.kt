package com.byansanur.campuslist.data.network.api

import com.byansanur.campuslist.data.network.response.CampusResponse
import com.byansanur.campuslist.utils.Utils.MY_COUNTRY
import kotlinx.coroutines.flow.Flow

interface CampusApi {

    fun getCampus(country: String = MY_COUNTRY, limit: Int = 10, offset: Int = 0) :
            Flow<ApiResult<List<CampusResponse>>>

    fun getSearchCampus(country:String=MY_COUNTRY, name: String, limit: Int = 10, offset: Int = 0) :
            Flow<ApiResult<List<CampusResponse>>>
}