package com.byansanur.campuslist.presentation.lauch_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.byansanur.campuslist.R
import com.byansanur.campuslist.presentation.navigation.Screens
import com.byansanur.campuslist.presentation.navigation.Screens.CAMPUS_LIST_SCREEN
import kotlinx.coroutines.delay

@Composable
fun LauncherScreen(navController: NavController) {
    LaunchedEffect(key1 = true) {
        delay(1000L)
        navController.navigate(CAMPUS_LIST_SCREEN) {
            popUpTo(Screens.LAUNCHER_SCREEN) { inclusive = true }
        }
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(height = 300.dp)
        )
    }
}