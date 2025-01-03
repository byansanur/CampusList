package com.byansanur.campuslist.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.byansanur.campuslist.utils.FakeListCampus
import com.byansanur.campuslist.domain.repository.DomainRepository
import com.byansanur.campuslist.presentation.campus_screen.CampusViewModel
import com.byansanur.campuslist.utils.MainDispatcherRule
import com.byansanur.campuslist.utils.getOrAwaitValue
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.exceptions.base.MockitoException
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class CampusViewModelTest {

    @get:Rule(order = 0)
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule(order = 1)
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var domainRepository: DomainRepository

    private lateinit var viewModel: CampusViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        domainRepository = mock(DomainRepository::class.java) // Initialize mocks
        viewModel = CampusViewModel(domainRepository)
    }

    @Test
    fun `getCampuses - success - emits campus list`() = runTest {
        val campusList = FakeListCampus.generateFakeListCampus()
        Mockito.`when`(domainRepository.getCampus(false)).thenReturn(campusList)

        val result = viewModel.getCampuses().toList()
        val expeected = listOf(campusList).size

        assertEquals(expeected, result.size)
        assertEquals(campusList, result.first())
    }

    @Test
    fun `getCampuses - failure - emits error`() = runTest {
        val errorMessage = "Network Error"
        val exception = MockitoException(errorMessage)
        Mockito.`when`(domainRepository.getCampus(false)).thenThrow(exception)

        viewModel.getCampuses()
        advanceUntilIdle()
        val error = viewModel.updateLivedataError(errorMessage).getOrAwaitValue()

        assertEquals(errorMessage, error)
    }

    @Test
    fun `getRecentSearch - success - emits campus list`() = runTest {
        val campusList = FakeListCampus.generateFakeListCampus()
        Mockito.`when`(domainRepository.getRecentSearch()).thenReturn(campusList)

        val result = viewModel.getRecentSearch().toList()
        val expeected = listOf(campusList).size

        assertEquals(expeected, result.size)
        assertEquals(campusList, result.first())
    }

    @Test
    fun `getCampusByName - success - emits campus by name`() = runTest {
        val campusList = FakeListCampus.generateFakeListCampus().first()
        Mockito.`when`(domainRepository.getCampusByName("Fake list 1")).thenReturn(campusList)

        viewModel.getCampusByName("Fake list 1")

        assertEquals(campusList.name, "Fake list 1")
    }
}