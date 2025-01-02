package com.byansanur.campuslist.domain.repository

import com.byansanur.campuslist.domain.model.Campus

interface CampusRepository {
    suspend fun getCampus(refresh: Boolean): List<Campus>
    suspend fun getSearchCampus(name: String, refresh: Boolean): List<Campus>
    suspend fun getCampusByName(name: String): Campus?
    suspend fun getRecentSearch(): List<Campus>
}