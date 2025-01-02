package com.byansanur.campuslist.data.network.response

import com.byansanur.campuslist.data.entity.CampusModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CampusResponse(
    @SerialName("alpha_two_code")
    val alphaTwoCode: String? = "",
    @SerialName("state-province")
    val stateProvince: String? = "",
    @SerialName("country")
    val country: String? = "",
    @SerialName("domains")
    val domains: List<String>? = listOf(),
    @SerialName("name")
    val name: String,
    @SerialName("web_pages")
    val webPages: List<String>? = listOf()
)

fun CampusResponse.toData() = CampusModel(
    alphaTwoCode = alphaTwoCode,
    country = country,
    domains = domains,
    name = name,
    webPages = webPages
)
