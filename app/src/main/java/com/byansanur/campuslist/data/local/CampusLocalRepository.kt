package com.byansanur.campuslist.data.local

import com.byansanur.campuslist.data.local.dao.CampusDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import com.byansanur.campuslist.data.entity.CampusModel
import com.byansanur.campuslist.data.entity.toLocal
import com.byansanur.campuslist.data.local.entity.toData

class CampusLocalRepository @Inject constructor(
    private val campusDao: CampusDao,
    private val preferencesHelper: PreferenceHelpers
) {

    companion object {
        private const val EXPIRATION_TIME = (60 * 10 * 1000).toLong()
    }

    suspend fun getAllCampus(): List<CampusModel> = withContext(Dispatchers.IO) {
        campusDao.getCampusList().map { it.toData() }
    }

    suspend fun getCampusById(id: Int) : CampusModel = withContext(Dispatchers.IO) {
        campusDao.getCampusById(id).toData()
    }

    suspend fun insertCampus(campus: List<CampusModel>) = withContext(Dispatchers.IO) {
        campusDao.deleteCampusList()
        campusDao.insertCampus(campus.map { it.toLocal() })
        setLastCacheTime(System.currentTimeMillis())

    }

    private fun setLastCacheTime(lastCache: Long) {
        preferencesHelper.lastCacheTime = lastCache
    }

    private fun getLastCacheUpdateTimeMillis(): Long {
        return preferencesHelper.lastCacheTime
    }

    fun isExpired(): Boolean {
        val currentTime = System.currentTimeMillis()
        val lastUpdateTime = this.getLastCacheUpdateTimeMillis()
        return currentTime - lastUpdateTime > EXPIRATION_TIME
    }

}