package com.byansanur.campuslist.presentation.campus_screen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.byansanur.campuslist.domain.model.Campus
import com.byansanur.campuslist.domain.repository.DomainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CampusViewModel @Inject constructor(
    private val domainRepository: DomainRepository
) : ViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    private val _error = MutableLiveData<String>()

    val loading: LiveData<Boolean> get() = _loading
    val error: LiveData<String> get() = _error

    private val _listCampus = MutableLiveData<List<Campus>>()
    val listCampus: LiveData<List<Campus>> get() = _listCampus

    init {
        getCampuses()
    }

    private fun getCampuses(refresh: Boolean = false) {
        viewModelScope.launch {
            try {
                _loading.value = true
                _listCampus.value = domainRepository.getCampus(refresh)
            } catch (exception: Exception) {
                _loading.value = false
                _error.value = exception.message
            } finally {
                _loading.value = false
            }
        }
    }

    fun searchCampus(name: String): Flow<List<Campus>> = flow {
        if (name.isBlank()) { // Check for empty or blank search string
            emit(emptyList()) // Emit an empty list for empty searches
            return@flow // Early return to avoid unnecessary API call
        }

        Log.e("TAG", "getSearchCampus: $name")
        try {
            _loading.postValue(true)
            val campusList = domainRepository.getSearchCampus(name, false)
            Log.e("TAG", "searchCampus: $campusList", )
            emit(campusList) // Emit the fetched data
        } catch (exception: Exception) {
            _error.postValue(exception.message) // Update LiveData (if needed)
            Log.e("Error", exception.message.toString())
            emit(emptyList()) // Emit an empty list on error
        } finally {
            _loading.postValue(false)
        }
    }

    fun getCampusByName(name: String): Flow<Campus> = flow {
        if (name.isBlank()) {
            return@flow
        }
        try {
            _loading.postValue(true)
            val campus = domainRepository.getCampusByName(name)
            Log.e("TAG", "searchCampus: $campus", )
            emit(campus) // Emit the fetched data
        } catch (exception: Exception) {
            _error.postValue(exception.message) // Update LiveData (if needed)
            Log.e("Error", exception.message.toString())
        } finally {
            _loading.postValue(false)
        }
    }


}