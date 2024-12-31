package com.byansanur.campuslist.domain

data class Campus(
    val id: Int = 0,
    val alphaTwoCode: String? = "",
    val country: String? = "",
    val domains: List<String>? = listOf(),
    val name: String? = "",
    val webPages: List<String>? = listOf()
)
