package com.byansanur.campuslist.data.entity

import com.byansanur.campuslist.data.local.entity.CampusEntity
import com.byansanur.campuslist.data.local.entity.CampusSearchEntity
import com.byansanur.campuslist.domain.model.Campus

data class CampusModel(
    val alphaTwoCode: String? = "",
    val country: String? = "",
    val domains: List<String>? = listOf(),
    val name: String,
    val webPages: List<String>? = listOf()
)

fun CampusModel.toLocal() = CampusEntity(
    alphaTwoCode = alphaTwoCode,
    country = country,
    domains = domains,
    name = name,
    webPages = webPages
)

fun CampusModel.toLocalSearch() = CampusSearchEntity(
    alphaTwoCode = alphaTwoCode,
    country = country,
    domains = domains,
    name = name,
    webPages = webPages
)

fun CampusModel.toDomain() = Campus(
    alphaTwoCode = alphaTwoCode,
    country = country,
    domains = domains,
    name = name,
    webPages = webPages
)





