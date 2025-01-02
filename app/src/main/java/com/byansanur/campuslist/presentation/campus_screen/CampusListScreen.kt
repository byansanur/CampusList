package com.byansanur.campuslist.presentation.campus_screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.byansanur.campuslist.R
import com.byansanur.campuslist.domain.model.Campus
import com.byansanur.campuslist.presentation.generic_views.CampusItemView
import com.byansanur.campuslist.presentation.generic_views.CustomCenterToolbar
import com.byansanur.campuslist.presentation.generic_views.MySearchBar
import com.byansanur.campuslist.presentation.generic_views.ShowDialog
import com.byansanur.campuslist.presentation.generic_views.StartDefaultLoader
import com.byansanur.campuslist.presentation.navigation.Screens.CAMPUS_DETAILS_SCREEN
import com.byansanur.campuslist.presentation.navigation.Screens.CAMPUS_SEARCH_SCREEN
import kotlinx.coroutines.delay


@Composable
internal fun CampusListScreen(navController: NavController) {
    Scaffold(
        topBar = {
            Column {
                CustomCenterToolbar(R.string.app_name)
                Box(
                    modifier = Modifier
                        .wrapContentWidth()
                        .fillMaxWidth()
                        .background(Color.DarkGray)
                        .padding(start = 15.dp, end = 15.dp, bottom = 15.dp),
                ) {
                    SearchCampusBar(navController)
                }
            }

        },
        content = { paddingValue ->
            ListOfCampuses(navController = navController, paddingValues = paddingValue)

        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchCampusBar(
    navController: NavController,
) {
    var searchText by remember { mutableStateOf("") }
    var searchedText by remember { mutableStateOf("") } // Store the actual searched text

    MySearchBar(
        text = searchText,
        onTextChange = { text -> searchText = text },
        onSearchClicked = { // Regular lambda
            searchedText = searchText // Update the searched text
        }
    )

    // Conditionally compose ListOfCampuses based on searchedText
    if (searchedText.isNotEmpty()) {
        // navigation to campus search
        LaunchedEffect(key1 = true) {
            delay(100L)
            navController.navigate(CAMPUS_SEARCH_SCREEN+"/${searchedText}")
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListOfCampuses(
    navController: NavController,
    paddingValues: PaddingValues,
    campusViewModel: CampusViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val isLoading = campusViewModel.loading.observeAsState(initial = false).value
    if (isLoading) StartDefaultLoader()
    val error = campusViewModel.error.observeAsState(initial = "").value
    if (error.isNotEmpty()) ShowDialog(
        title = stringResource(id = R.string.error),
        message = error
    )

    var listCampus = mutableListOf<Campus>()
    campusViewModel.listCampus.observe(context as LifecycleOwner) {
        listCampus.addAll(it)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Log.d("TAG", "ListOfCampuses: $listCampus")
        items(listCampus, key = { campus -> campus.name.toString() }) {
            CampusItemView(campus = it) { selectedCampusDataValue ->
                Log.e("TAG", "ListOfCampuses: id: ${selectedCampusDataValue.name}")
                navController.navigate(CAMPUS_DETAILS_SCREEN + "/${selectedCampusDataValue.name}")
            }
        }
    }



}