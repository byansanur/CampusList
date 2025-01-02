package com.byansanur.campuslist.presentation.campus_screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
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
import kotlinx.coroutines.flow.collectLatest


@Composable
internal fun CampusListScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        CustomCenterToolbar(R.string.app_name)
        Box(
            modifier = Modifier
                .wrapContentWidth()
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(start = 15.dp, end = 15.dp, bottom = 15.dp),
        ) {
            SearchCampusBar(navController)
        }
        ColumnView(navController)
    }
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
            navController.navigate(CAMPUS_SEARCH_SCREEN + "/${searchedText}")
        }
    }
}

@Composable
fun ColumnView(
    navController: NavController
) {
    Column {
        RecentSearch(navController)
        ListOfCampuses(navController = navController)
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListOfCampuses(
    navController: NavController,
    campusViewModel: CampusViewModel = hiltViewModel()
) {
    val isLoading = campusViewModel.loading.observeAsState(initial = false).value
    if (isLoading) StartDefaultLoader()
    val error = campusViewModel.error.observeAsState(initial = "").value
    if (error.isNotEmpty()) ShowDialog(
        title = stringResource(id = R.string.error),
        message = error,
        onClickOk = {}
    )

    val listCampus by produceState<List<Campus>>(initialValue = emptyList(), key1 = false) {
        campusViewModel.getCampuses().collectLatest { data ->
            value = if (data.isEmpty())
                emptyList()
            else data
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
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

@Composable
fun RecentSearch(
    navController: NavController,
    campusViewModel: CampusViewModel = hiltViewModel()
) {
    val recentSearchCampus by produceState<List<Campus>>(initialValue = emptyList(), key1 = false) {
        campusViewModel.getRecentSearch().collectLatest { data ->
            value = if (data.isEmpty())
                emptyList()
            else data
        }
    }

    if (recentSearchCampus.isNotEmpty()) {
        Log.e("TAG", "RecentSearch: $recentSearchCampus")
        Column(
            modifier = Modifier.padding(PaddingValues(start = 16.dp))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(), // Optional padding around the Row
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                content = {
                    Text(
                        text = stringResource(R.string.recent_search),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            )
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 48.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(recentSearchCampus) { item ->
                    FilterChip(
                        selected = false,
                        onClick = {
                            Log.e("TAG", "RecentSearch: ${item.name.toString()}")
                            navController.navigate(CAMPUS_DETAILS_SCREEN + "/${item.name}")
                        },
                        label = { Text(item.name.toString()) },
                        border = BorderStroke(1.dp, Color.Gray),
                        shape = RoundedCornerShape(16.dp),
                        colors = FilterChipDefaults.filterChipColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant,
                            labelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    )
                }
            }
        }
    }
}