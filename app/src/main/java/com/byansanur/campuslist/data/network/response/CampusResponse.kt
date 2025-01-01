package com.byansanur.campuslist.data.network.response
import com.byansanur.campuslist.data.entity.CampusModel
import com.google.gson.annotations.SerializedName


data class CampusResponse(
    @SerializedName("alpha_two_code")
    val alphaTwoCode: String? = "",
    @SerializedName("country")
    val country: String? = "",
    @SerializedName("domains")
    val domains: List<String>? = listOf(),
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("web_pages")
    val webPages: List<String>? = listOf()
)

fun CampusResponse.toData() = CampusModel(
    alphaTwoCode = alphaTwoCode,
    country = country,
    domains = domains,
    name = name,
    webPages = webPages
)
