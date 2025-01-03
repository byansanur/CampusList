package com.byansanur.campuslist.utils

import com.byansanur.campuslist.data.entity.CampusModel
import com.byansanur.campuslist.data.local.entity.CampusEntity
import com.byansanur.campuslist.data.local.entity.CampusSearchEntity
import com.byansanur.campuslist.domain.model.Campus

object FakeListCampus {
    fun generateFakeListCampusEntity(): List<CampusEntity> {
        val campusList = mutableListOf<CampusEntity>()
        val campusFirst = CampusEntity(
            alphaTwoCode = "ID",
            country = "Indonesia",
            domains = listOf("fakeCampus.ac.id"),
            name = "Fake list 1",
            webPages = listOf("https://fakeCampus.ac.id")
        )
        campusList.add(campusFirst)
        val campusSecond = CampusEntity(
            alphaTwoCode = "ID",
            country = "Indonesia",
            domains = listOf("fakeCampus2.ac.id"),
            name = "Fake list 2",
            webPages = listOf("https://fakeCampus2.ac.id")
        )
        campusList.add(campusSecond)
        val campusThird = CampusEntity(
            alphaTwoCode = "ID",
            country = "Indonesia",
            domains = listOf("fakeCampus3.ac.id"),
            name = "Fake list 3",
            webPages = listOf("https://fakeCampus3.ac.id")
        )
        campusList.add(campusThird)
        return campusList
    }

    fun generateFakeSearchListCampus(): List<CampusSearchEntity> {
        val campusList = mutableListOf<CampusSearchEntity>()
        val campusFirst = CampusSearchEntity(
            alphaTwoCode = "ID",
            country = "Indonesia",
            domains = listOf("fakeCampus.ac.id"),
            name = "Fake list 1",
            webPages = listOf("https://fakeCampus.ac.id")
        )
        campusList.add(campusFirst)
        val campusSecond = CampusSearchEntity(
            alphaTwoCode = "ID",
            country = "Indonesia",
            domains = listOf("fakeCampus2.ac.id"),
            name = "Fake list 2",
            webPages = listOf("https://fakeCampus2.ac.id")
        )
        campusList.add(campusSecond)
        val campusThird = CampusSearchEntity(
            alphaTwoCode = "ID",
            country = "Indonesia",
            domains = listOf("fakeCampus3.ac.id"),
            name = "Fake list 3",
            webPages = listOf("https://fakeCampus3.ac.id")
        )
        campusList.add(campusThird)
        return campusList
    }

    fun generateFakeListCampus(): List<Campus> {
        val campusList = mutableListOf<Campus>()
        val campusFirst = Campus(
            alphaTwoCode = "ID",
            country = "Indonesia",
            domains = listOf("fakeCampus.ac.id"),
            name = "Fake list 1",
            webPages = listOf("https://fakeCampus.ac.id")
        )
        campusList.add(campusFirst)
        val campusSecond = Campus(
            alphaTwoCode = "ID",
            country = "Indonesia",
            domains = listOf("fakeCampus2.ac.id"),
            name = "Fake list 2",
            webPages = listOf("https://fakeCampus2.ac.id")
        )
        campusList.add(campusSecond)
        val campusThird = Campus(
            alphaTwoCode = "ID",
            country = "Indonesia",
            domains = listOf("fakeCampus3.ac.id"),
            name = "Fake list 3",
            webPages = listOf("https://fakeCampus3.ac.id")
        )
        campusList.add(campusThird)
        return campusList
    }

    fun generateCampusModel(): List<CampusModel> {
        val campusList = mutableListOf<CampusModel>()
        for (i in 0..5) {
            val campusThird = CampusModel(
                alphaTwoCode = "ID",
                country = "Indonesia",
                domains = listOf("fakeCampus3.ac.id"),
                name = "Fake list $i",
                webPages = listOf("https://fakeCampus3.ac.id")
            )
            campusList.add(campusThird)
        }
        return campusList
    }
}