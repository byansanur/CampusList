package com.byansanur.campuslist.domain.repository

import com.byansanur.campuslist.domain.usecase.GetCampusByNameUseCase
import com.byansanur.campuslist.domain.usecase.GetCampusUseCase
import com.byansanur.campuslist.domain.usecase.GetRecentSearchUseCase
import com.byansanur.campuslist.domain.usecase.GetSearchCampusUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DomainRepository @Inject constructor(
    private val getCampusUseCase: GetCampusUseCase,
    private val getSearchCampusUseCase: GetSearchCampusUseCase,
    private val getCampusByNameUseCase: GetCampusByNameUseCase,
    private val getRecentSearchUseCase: GetRecentSearchUseCase,
) {
    suspend fun getCampus(refresh: Boolean) =
        getCampusUseCase.invoke(refresh)

    suspend fun getSearchCampus(name: String, refresh: Boolean) =
        getSearchCampusUseCase.invoke(name, refresh)

    suspend fun getCampusByName(name: String) =
        getCampusByNameUseCase.invoke(name)

    suspend fun getRecentSearch() = getRecentSearchUseCase.invoke()
}