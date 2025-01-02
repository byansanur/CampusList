package com.byansanur.campuslist.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.byansanur.campuslist.data.entity.CampusModel

@Entity(tableName = "tb_campus")
data class CampusEntity(
    @ColumnInfo(name = "alpha_two_code")
    var alphaTwoCode: String? = "",
    var country: String? = "",
    @TypeConverters(ListStringConverter::class)
    @ColumnInfo(name = "domains")
    var domains: List<String>? = listOf(),
    @PrimaryKey
    var name: String,
    @TypeConverters(ListStringConverter::class)
    @ColumnInfo(name = "web_pages")
    var webPages: List<String>? = listOf()
)

fun CampusEntity.toData() = CampusModel(
    alphaTwoCode,
    country,
    domains,
    name,
    webPages
)