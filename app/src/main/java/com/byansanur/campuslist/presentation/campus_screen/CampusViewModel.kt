package com.byansanur.campuslist.presentation.campus_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.byansanur.campuslist.domain.model.Campus
import com.byansanur.campuslist.domain.repository.DomainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class CampusViewModel @Inject constructor(
    private val domainRepository: DomainRepository
) : ViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    private val _error = MutableLiveData<String>()

    val loading: LiveData<Boolean> get() = _loading
    val error: LiveData<String> get() = _error


    fun getCampuses(refresh: Boolean = false): Flow<List<Campus>> = flow {
        try {
            _loading.value = true
            val campus = domainRepository.getCampus(refresh)
            emit(campus)
        } catch (exception: Exception) {
            _error.value = exception.message
            emit(emptyList())
            updateLivedataError(exception.message!!)
        } finally {
            _loading.value = false
        }
    }

    fun updateLivedataError(newValue: String) : LiveData<String> {
        var mutableSt =  MutableLiveData<String>()
        mutableSt.value = newValue
        return mutableSt
    }

    fun searchCampus(name: String): Flow<List<Campus>> = flow {
        if (name.isBlank()) {
            emit(emptyList())
            return@flow
        }

        try {
            _loading.postValue(true)
            val campusList = domainRepository.getSearchCampus(name, false)
            emit(campusList)
        } catch (exception: Exception) {
            _error.postValue(exception.message)
            emit(emptyList())
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
            campus?.let { emit(it) }
        } catch (exception: Exception) {
            _error.postValue(exception.message)
        } finally {
            _loading.postValue(false)
        }
    }

    fun getRecentSearch(): Flow<List<Campus>> = flow {
        try {
            _loading.postValue(true)
            val campusList = domainRepository.getRecentSearch()
            emit(campusList)
        } catch (exception: Exception) {
            _error.postValue(exception.message)
            emit(emptyList())
        } finally {
            _loading.postValue(false)
        }
    }


}