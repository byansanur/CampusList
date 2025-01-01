package com.byansanur.campuslist.domain.usecase

import com.byansanur.campuslist.domain.model.Campus
import com.byansanur.campuslist.domain.repository.CampusRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CampusUseCaseImpl @Inject constructor(
    private val campusRepository: CampusRepository
) : CampusUseCase {
    override suspend fun getCampus(refresh: Boolean): List<Campus> = withContext(Dispatchers.IO) {
        campusRepository.getCampus(refresh)
    }

    override suspend fun getSearchCampus(name: String): List<Campus> = withContext(Dispatchers.IO) {
        campusRepository.getSearchCampus(name)
    }

    override suspend fun getCampusById(userId: Int): Campus = withContext(Dispatchers.IO) {
        campusRepository.getCampusById(userId)
    }
}