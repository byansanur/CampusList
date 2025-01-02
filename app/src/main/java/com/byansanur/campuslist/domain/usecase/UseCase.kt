package com.byansanur.campuslist.domain.usecase

import com.byansanur.campuslist.data.repository.DataRepository
import javax.inject.Inject

class GetCampusUseCase @Inject constructor(
    private val getCampus: DataRepository
) {
    suspend operator fun invoke(refresh: Boolean) =
        getCampus.getCampus(refresh)
}

class GetSearchCampusUseCase @Inject constructor(
    private val getSearchCampus: DataRepository
) {
    suspend operator fun invoke(name: String, refresh: Boolean) =
        getSearchCampus.getSearchCampus(name, refresh)
}

class GetCampusByNameUseCase @Inject constructor(
    private val getCampusByName: DataRepository
) {
    suspend operator fun invoke(name: String) =
        getCampusByName.getCampusByName(name)
}