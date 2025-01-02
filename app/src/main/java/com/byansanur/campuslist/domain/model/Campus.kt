package com.byansanur.campuslist.domain.model

import com.byansanur.campuslist.data.entity.CampusModel

data class Campus(
    val alphaTwoCode: String? = "",
    val country: String? = "",
    val domains: List<String>? = listOf(),
    val name: String? = "",
    val webPages: List<String>? = listOf()
)

fun Campus.toLocal() = CampusModel(
    alphaTwoCode = alphaTwoCode,
    country = country,
    domains = domains,
    name = name.toString(),
    webPages = webPages
)
