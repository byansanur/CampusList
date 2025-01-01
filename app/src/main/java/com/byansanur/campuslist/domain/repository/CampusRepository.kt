package com.byansanur.campuslist.domain.repository

import com.byansanur.campuslist.domain.Campus

interface CampusRepository {
    suspend fun getCampus(refresh: Boolean): List<Campus>
    suspend fun getSearchCampus(name: String): List<Campus>
    suspend fun getCampusById(userId: Int): Campus
}