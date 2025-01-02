package com.byansanur.campuslist.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.byansanur.campuslist.presentation.campus_screen.CampusListScreen
import com.byansanur.campuslist.presentation.campus_screen.detail.CampusDetailScreen
import com.byansanur.campuslist.presentation.campus_screen.search.CampusSearchScreen
import com.byansanur.campuslist.presentation.lauch_screen.LauncherScreen
import com.byansanur.campuslist.presentation.navigation.Screens.CAMPUS_DETAILS_SCREEN
import com.byansanur.campuslist.utils.Utils.CAMPUS_NAME_KEY
import com.byansanur.campuslist.utils.Utils.CAMPUS_SEARCH_KEY

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screens.LAUNCHER_SCREEN
    ) {
        composable(Screens.LAUNCHER_SCREEN) {
            LauncherScreen(navController = navController)
        }
        composable(Screens.CAMPUS_LIST_SCREEN) {
            CampusListScreen(navController)
        }
        composable("${Screens.CAMPUS_SEARCH_SCREEN}/{$CAMPUS_SEARCH_KEY}", arguments = listOf(
            navArgument(CAMPUS_SEARCH_KEY) {
                type = NavType.StringType
                defaultValue = null
                nullable = true
            }
        )) {
            CampusSearchScreen(navController, searchKey = it.arguments?.getString(CAMPUS_SEARCH_KEY, null)!!)
        }
        composable("${CAMPUS_DETAILS_SCREEN}/{$CAMPUS_NAME_KEY}", arguments = listOf(
            navArgument(CAMPUS_NAME_KEY) {
                type = NavType.StringType
                defaultValue = null
                nullable = true
            }
        )) {
            CampusDetailScreen(navController, name = it.arguments!!.getString(CAMPUS_NAME_KEY)!!)
        }
    }
}