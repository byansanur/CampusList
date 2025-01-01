package com.byansanur.campuslist.domain.usecase

import com.byansanur.campuslist.domain.model.Campus

interface CampusUseCase {
    suspend fun getCampus(refresh: Boolean): List<Campus>
    suspend fun getSearchCampus(name: String): List<Campus>
    suspend fun getCampusById(userId: Int): Campus
}