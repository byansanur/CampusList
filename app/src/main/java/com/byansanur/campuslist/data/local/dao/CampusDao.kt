package com.byansanur.campuslist.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.byansanur.campuslist.data.local.entity.CampusEntity

@Dao
interface CampusDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCampus(campusListEntity: List<CampusEntity>)

    @Query("SELECT * FROM tb_campus")
    suspend fun getCampusList() : List<CampusEntity>

    @Query("SELECT * FROM tb_campus WHERE id = :id")
    suspend fun getCampusById(id: Int) : CampusEntity

    @Query("DELETE FROM tb_campus")
    suspend fun deleteCampusList()
}