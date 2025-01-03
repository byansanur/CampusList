package com.byansanur.campuslist.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.byansanur.campuslist.data.local.CampusLocalRepository
import com.byansanur.campuslist.data.network.NetworkDataSource
import com.byansanur.campuslist.data.repository.DataRepository
import com.byansanur.campuslist.utils.FakeListCampus
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class DataRepositoryTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var repository: DataRepository
    private lateinit var mockLocalRepo: CampusLocalRepository
    private lateinit var mockRemoteSource: NetworkDataSource

    private val mockCampusList = FakeListCampus.generateFakeListCampus()

    @Before
    fun setUp() {
        mockLocalRepo = mockk<CampusLocalRepository>()
        mockRemoteSource = mockk<NetworkDataSource>()
        repository = DataRepository(mockLocalRepo, mockRemoteSource)

        coEvery { mockLocalRepo.getAllCampus() } returns FakeListCampus.generateCampusModel()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `getCampus returns data from local when not empty and not refresh`() = runBlocking {
        val results = repository.getCampus(refresh = false)

        assertEquals(mockCampusList, results)
        coVerify(exactly = 0) { mockRemoteSource.getCampusList() } // Verify no remote call
    }
}