package com.byansanur.campuslist.domain.model

import com.byansanur.campuslist.data.entity.CampusModel

data class Campus(
    val id: Int = 0,
    val alphaTwoCode: String? = "",
    val country: String? = "",
    val domains: List<String>? = listOf(),
    val name: String? = "",
    val webPages: List<String>? = listOf()
)

fun Campus.toLocal() = CampusModel(
    id = id,
    alphaTwoCode = alphaTwoCode,
    country = country,
    domains = domains,
    name = name,
    webPages = webPages
)
