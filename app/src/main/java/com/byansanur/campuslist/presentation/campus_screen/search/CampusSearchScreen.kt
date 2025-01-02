package com.byansanur.campuslist.presentation.campus_screen.search

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.byansanur.campuslist.domain.model.Campus
import com.byansanur.campuslist.presentation.campus_screen.CampusViewModel
import com.byansanur.campuslist.presentation.generic_views.CampusItemView
import com.byansanur.campuslist.presentation.generic_views.CustomToolbar
import com.byansanur.campuslist.presentation.navigation.Screens.CAMPUS_DETAILS_SCREEN
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun CampusSearchScreen(
    navController: NavController,
    searchKey: String
) {
    Scaffold(
        topBar = {
            CustomToolbar(
                "Search \"$searchKey\"",
                navigateBack = { navController.popBackStack() }
            )
        },
        content = { paddingValue ->
            ListOfSearchCampus(
                navController = navController,
                paddingValues = paddingValue,
                searchKey = searchKey
            )
        })
}


@SuppressLint("ProduceStateDoesNotAssignValue")
@Composable
fun ListOfSearchCampus(
    navController: NavController,
    paddingValues: PaddingValues,
    searchKey: String,
    viewModel: CampusViewModel = hiltViewModel()
) {

    val context = LocalContext.current


    val campuses by produceState<List<Campus>>(initialValue = emptyList(), key1 = searchKey) {
        if (searchKey.isNotEmpty()) {
            viewModel.searchCampus(searchKey).collectLatest { campusList ->
                Log.d("TAG", "ListOfSearchCampus: produceState: $campusList")
                value = campusList
            }
        } else {
            value = emptyList()
        }
    }
    val isLoading by viewModel.loading.observeAsState(initial = false)
    val error by viewModel.error.observeAsState()

    if (isLoading) {
        CircularProgressIndicator()
    }
    if(error != null){
        Text(text = error!!)
    }
    Log.d("TAG", "ListOfSearchCampus: campuses: $campuses")
    if (campuses.isNotEmpty()) {
        // Display your list of campuses here
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(campuses, key = {campus -> campus.name.toString()}) {
                CampusItemView(campus = it) { selectedCampusDataValue ->
                    Toast.makeText(
                        context,
                        selectedCampusDataValue.name.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                    navController.navigate(CAMPUS_DETAILS_SCREEN + "/${selectedCampusDataValue.name}")
                }
            }
        }
    }
}
