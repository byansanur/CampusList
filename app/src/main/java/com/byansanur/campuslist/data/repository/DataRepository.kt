package com.byansanur.campuslist.data.repository

import com.byansanur.campuslist.data.entity.toDomain
import com.byansanur.campuslist.data.entity.toLocal
import com.byansanur.campuslist.data.local.CampusLocalRepository
import com.byansanur.campuslist.data.network.NetworkDataSource
import com.byansanur.campuslist.domain.model.Campus
import com.byansanur.campuslist.domain.repository.CampusRepository
import com.byansanur.campuslist.domain.model.toLocal
import com.byansanur.campuslist.utils.flattenToList
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataRepository @Inject constructor(
    private val localRepo: CampusLocalRepository,
    private val remoteSource: NetworkDataSource
) : CampusRepository {
    override suspend fun getCampus(refresh: Boolean): List<Campus> = withContext(Dispatchers.IO) {
        val campusLocalList = getCampusFromLocal()
        if (campusLocalList.isNotEmpty() &&  !refresh) {
            if (localRepo.isExpired()) {
                getCampusFromRemote()
                delay(200)
                getCampusFromLocal()
            } else {
                campusLocalList
            }
        } else {
            getCampusFromRemote()
            delay(200)
            getCampusFromLocal()
        }
    }

    override suspend fun getSearchCampus(name: String, refresh: Boolean): List<Campus> = withContext(Dispatchers.IO) {
        val searchLocalList = getCampusBySearchFromLocal(name)
        if (searchLocalList.isNotEmpty()&& !refresh) {
            if (localRepo.isExpired()) {
                getCampusBySearchFromRemote(name)
            } else {
                searchLocalList
            }
        } else {
            getCampusBySearchFromRemote(name)
        }
    }

    override suspend fun getCampusByName(name: String): Campus = withContext(Dispatchers.IO) {
        localRepo.getCampusByName(name).toDomain()
    }

    private suspend fun getCampusBySearchFromLocal(name: String): List<Campus> = withContext(Dispatchers.IO) {
        localRepo.getCampusBySearch(name).map { it.toDomain() }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private suspend fun getCampusBySearchFromRemote(name: String): List<Campus> {
        val remoteSearchCampuses = remoteSource.getSearchCampus(name).map { it }

        val collectedSearchCampuses: List<Campus> = remoteSearchCampuses.flattenToList().map { it.toDomain() }

        if (collectedSearchCampuses.isNotEmpty())
            GlobalScope.launch(Dispatchers.IO) {
                localRepo.insertCampus(collectedSearchCampuses.map { it.toLocal() })
            }
        else getCampusBySearchFromLocal(name)

        return collectedSearchCampuses
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