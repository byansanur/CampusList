package com.byansanur.campuslist.data.entity

import com.byansanur.campuslist.data.local.entity.CampusEntity
import com.byansanur.campuslist.domain.Campus

data class CampusModel(
    val id: Int = 0,
    val alphaTwoCode: String? = "",
    val country: String? = "",
    val domains: List<String>? = listOf(),
    val name: String? = "",
    val webPages: List<String>? = listOf()
)

fun CampusModel.toLocal() = CampusEntity(
    id = id,
    alphaTwoCode = alphaTwoCode,
    country = country,
    domains = domains,
    name = name,
    webPages = webPages
)

fun CampusModel.toDomain() = Campus(
    id = id,
    alphaTwoCode = alphaTwoCode,
    country = country,
    domains = domains,
    name = name,
    webPages = webPages
)





