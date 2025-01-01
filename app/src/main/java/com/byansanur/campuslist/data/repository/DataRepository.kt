package com.byansanur.campuslist.data.repository

import com.byansanur.campuslist.data.entity.toDomain
import com.byansanur.campuslist.data.local.CampusLocalRepository
import com.byansanur.campuslist.data.network.NetworkDataSource
import com.byansanur.campuslist.domain.model.Campus
import com.byansanur.campuslist.domain.repository.CampusRepository
import com.byansanur.campuslist.domain.model.toLocal
import com.byansanur.campuslist.utils.flattenToList
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DataRepository @Inject constructor(
    private val localRepo: CampusLocalRepository,
    private val remoteSource: NetworkDataSource
) : CampusRepository {
    override suspend fun getCampus(refresh: Boolean): List<Campus> = withContext(Dispatchers.IO) {
        val userLocalList = getCampusFromLocal()
        if (userLocalList.isNotEmpty() &&  !refresh) {
            if (localRepo.isExpired()) {
                getCampusFromRemote()
            } else {
                userLocalList
            }
        } else {
            getCampusFromRemote()
        }
    }

    override suspend fun getSearchCampus(name: String): List<Campus> = withContext(Dispatchers.IO) {
        val remoteSearch = remoteSource.getSearchCampus(name)
        remoteSearch.flattenToList().map { it.toDomain() }
    }

    override suspend fun getCampusById(userId: Int): Campus = withContext(Dispatchers.IO) {
        localRepo.getCampusById(userId).toDomain()
    }


    private suspend fun getCampusFromLocal(): List<Campus> = withContext(Dispatchers.IO) {
        localRepo.getAllCampus().map { it.toDomain() }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private suspend fun getCampusFromRemote(): List<Campus> {
        val remoteCampuses = remoteSource.getCampusList().map { it }

        val collectedCampuses: List<Campus> = remoteCampuses.flattenToList().map { it.toDomain() }

        if (collectedCampuses.isNotEmpty())
            GlobalScope.launch(Dispatchers.IO) {
                localRepo.insertCampus(collectedCampuses.map { it.toLocal() })
            }
        else getCampusFromLocal()

        return collectedCampuses
    }
}