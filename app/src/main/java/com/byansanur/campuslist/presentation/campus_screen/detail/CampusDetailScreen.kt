package com.byansanur.campuslist.presentation.campus_screen.detail

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.byansanur.campuslist.R
import com.byansanur.campuslist.domain.model.Campus
import com.byansanur.campuslist.presentation.campus_screen.CampusViewModel
import com.byansanur.campuslist.presentation.generic_views.CustomToolbar
import com.byansanur.campuslist.presentation.generic_views.ShowDialog
import kotlinx.coroutines.runBlocking


@Composable
internal fun CampusDetailScreen(
    navController: NavController,
    name: String,
    viewModel: CampusViewModel = hiltViewModel()
) {
    var campus = Campus()
    runBlocking {
        viewModel.getCampusByName(name).collect {
            campus = it
        }
    }

    if (campus.name?.isNotEmpty() == true)
        Column {
            CustomToolbar(
                campus.name.toString(),
                navigateBack = { navController.popBackStack() }
            )
            WebViewPage(campus.domains?.get(0).toString())
        }
    else {
        ShowDialog(
            title = stringResource(R.string.oops_sorry),
            message = stringResource(R.string.something_went_wrong),
            onClickOk = {
                navController.popBackStack()
            }
        )
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewPage(domain: String) {
    var isLoading by remember { mutableStateOf(true) }
    val context = LocalContext.current
    val webView = remember {
        WebView(context).apply {
            webViewClient = object : WebViewClient() {
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                    isLoading = true
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    isLoading = false
                }
            }
            settings.javaScriptEnabled = true
            settings.cacheMode = WebSettings.LOAD_NO_CACHE
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { webView },
            update = {
                val url = StringBuilder("https://$domain")
                it.loadUrl(url.toString())
            })

        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}