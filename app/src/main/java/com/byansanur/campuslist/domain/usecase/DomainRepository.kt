package com.byansanur.campuslist.domain.usecase

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DomainRepository @Inject constructor(
    private val getCampusUseCase: GetCampusUseCase,
    private val getSearchCampusUseCase: GetSearchCampusUseCase,
    private val getCampusByNameUseCase: GetCampusByNameUseCase
) {
    suspend fun getCampus(refresh: Boolean) =
        getCampusUseCase.invoke(refresh)

    suspend fun getSearchCampus(name: String, refresh: Boolean) =
        getSearchCampusUseCase.invoke(name, refresh)

    suspend fun getCampusByName(name: String) =
        getCampusByNameUseCase.invoke(name)
}