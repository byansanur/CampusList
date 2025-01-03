package com.byansanur.campuslist.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.byansanur.campuslist.utils.FakeListCampus
import com.byansanur.campuslist.data.entity.toLocal
import com.byansanur.campuslist.data.local.dao.CampusDao
import com.byansanur.campuslist.data.local.entity.CampusEntity
import com.byansanur.campuslist.data.local.entity.toData
import com.byansanur.campuslist.domain.model.toLocal
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

@RunWith(JUnit4::class)
class CampusLocalRepositoryTest {

    @get:Rule(order = 0)
    val instantExecutorRule = InstantTaskExecutorRule()


    private lateinit var campusDao: CampusDao

    private lateinit var preferencesHelper: PreferenceHelpers

    private lateinit var repository: CampusLocalRepository

    @Before
    fun setup() {
        campusDao = mock(CampusDao::class.java)
        preferencesHelper = mock(PreferenceHelpers::class.java)
        repository = CampusLocalRepository(campusDao, preferencesHelper)
    }

    @Test
    fun `getAllCampus - returns empty list if dao returns empty flow`() = runTest {
        // Mock the DAO to return an empty Flow
        Mockito.`when`(campusDao.getCampusList()).thenReturn(emptyList())

        // Call the repository method
        val campuses = repository.getAllCampus()

        // Verify the result
        assertTrue(campuses.isEmpty())
    }

    @Test
    fun `getAllCampus - maps DAO entities to domain models`() = runTest {
        val campusEntities: List<CampusEntity> = FakeListCampus.generateFakeListCampusEntity()
        val expectedCampuses = campusEntities.map { it.toData() }

        // Mock the DAO to return a Flow of entities
        Mockito.`when`(campusDao.getCampusList()).thenReturn(campusEntities)

        // Call the repository method
        val actualCampuses = repository.getAllCampus()

        // Verify the result
        assertEquals(expectedCampuses, actualCampuses.toList())
    }

    // Similar tests for getCampusBySearch and getCampusByName (omitted for brevity)

    @Test
    fun `getCampusByRecentSearchName - returns null if dao returns null`() = runTest {
        val name = "My Search"
        Mockito.`when`(campusDao.getCampusByRecentSearchName(name)).thenReturn(null)

        val campus = repository.getCampusByRecentSearchName(name)

        assertNull(campus)
    }

    @Test
    fun `getCampusByRecentSearchName - maps DAO entity to domain model`() = runTest {
        val campusEntity = FakeListCampus.generateFakeSearchListCampus().first()
        val expectedCampus = campusEntity.toData()
        val name = campusEntity.name

        Mockito.`when`(campusDao.getCampusByRecentSearchName(name.toString())).thenReturn(
            campusEntity
        )

        val actualCampus = repository.getCampusByRecentSearchName(name)

        assertEquals(expectedCampus, actualCampus)
    }

    @Test
    fun `insertCampus - calls insert on DAO`() = runTest {
        val campusModels = FakeListCampus.generateFakeListCampus().map { it.toLocal() }

        repository.insertCampus(campusModels)

        Mockito.verify(campusDao).insertCampus(campusModels.map { it.toLocal() })
    }

    @Test
    fun `insertCampus - calls insert on DAO and sets last cache time`() = runTest {
        val campusModels = FakeListCampus.generateFakeListCampusEntity()

        repository.insertCampus(campusModels.map { it.toData() })

        Mockito.verify(campusDao).insertCampus(campusModels.map { it.toData().toLocal() })
        Mockito.verify(preferencesHelper).lastCacheTime = Mockito.anyLong()
    }

    @Test
    fun `getRecentSearch - returns empty list if dao returns empty list`() = runTest {
        `when`(campusDao.getRecentSearch()).thenReturn(emptyList())

        val recentSearches = repository.getRecentSearch()

        assertTrue(recentSearches.isEmpty())
    }

    @Test
    fun `getRecentSearch - maps DAO entities to domain models`() = runTest {
        val recentSearchEntities = FakeListCampus.generateFakeSearchListCampus()
        val expectedRecentSearches = recentSearchEntities.map { it.toData() }

        `when`(campusDao.getRecentSearch()).thenReturn(recentSearchEntities)

        val actualRecentSearches = repository.getRecentSearch()

        assertEquals(expectedRecentSearches, actualRecentSearches)
    }

    @Test
    fun `isExpired - returns true if cache is expired`() {
        val currentTime = System.currentTimeMillis()
        val lastUpdateTime = currentTime - ((60 * 10 * 1000).toLong() + 1000) // Expired by 1 second

        `when`(preferencesHelper.lastCacheTime).thenReturn(lastUpdateTime)

        assertTrue(repository.isExpired())
    }

    @Test
    fun `isExpired - returns false if cache is not expired`() {
        val currentTime = System.currentTimeMillis()
        val lastUpdateTime = currentTime - ((60 * 10 * 1000).toLong() - 1000) // Not expired

        `when`(preferencesHelper.lastCacheTime).thenReturn(lastUpdateTime)

        assertFalse(repository.isExpired())
    }

    @Test
    fun `isExpired - returns false if last cache time is zero`(){
        `when`(preferencesHelper.lastCacheTime).thenReturn(0L)
        assertTrue(repository.isExpired())
    }
}