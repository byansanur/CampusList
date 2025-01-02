package com.byansanur.campuslist.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.byansanur.campuslist.data.local.dao.CampusDao
import com.byansanur.campuslist.data.local.entity.CampusEntity
import com.byansanur.campuslist.data.local.entity.CampusSearchEntity
import com.byansanur.campuslist.data.local.entity.ListStringConverter

@Database(
    entities = [CampusEntity::class, CampusSearchEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(ListStringConverter::class)
abstract class CampusDatabases : RoomDatabase() {
    abstract fun campusEntityDao(): CampusDao
}