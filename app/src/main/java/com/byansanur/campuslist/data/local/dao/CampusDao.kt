package com.byansanur.campuslist.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.byansanur.campuslist.data.local.entity.CampusEntity
import com.byansanur.campuslist.data.local.entity.CampusSearchEntity

@Dao
interface CampusDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCampus(campusListEntity: List<CampusEntity>)

    @Query("SELECT * FROM tb_campus")
    suspend fun getCampusList() : List<CampusEntity>

    @Query("SELECT * FROM tb_campus WHERE name = :name")
    suspend fun getCampusBySearch(name: String) : List<CampusEntity>

    @Query("SELECT * FROM tb_campus WHERE name = :name")
    suspend fun getCampusByName(name: String) : CampusEntity

    @Query("DELETE FROM tb_campus")
    suspend fun deleteCampusList()

    // recent search
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecentSearch(campusListEntity: List<CampusSearchEntity>)

    @Query("SELECT * FROM tb_recent_search ORDER BY search_timestamp DESC LIMIT 4")
    suspend fun getRecentSearch() : List<CampusSearchEntity>

    @Query("SELECT * FROM tb_recent_search WHERE name = :name")
    suspend fun getCampusByRecentSearchName(name: String) : CampusSearchEntity?
}