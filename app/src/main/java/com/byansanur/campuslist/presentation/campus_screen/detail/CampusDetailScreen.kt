package com.byansanur.campuslist.presentation.campus_screen.detail

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.util.Log
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.remember
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.byansanur.campuslist.domain.model.Campus
import com.byansanur.campuslist.presentation.campus_screen.CampusViewModel
import com.byansanur.campuslist.presentation.campus_screen.search.ListOfSearchCampus
import com.byansanur.campuslist.presentation.generic_views.CustomToolbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment


//@SuppressLint("ProduceStateDoesNotAssignValue")
@Composable
internal fun CampusDetailScreen(
    navController: NavController,
    name: String,
    viewModel: CampusViewModel = hiltViewModel()
) {
//    Log.e("TAG", "CampusDetailScreen: id: $name")
//    val campuses by produceState<Campus>(initialValue = Campus(), key1 = name) {
//        if (name.isNotEmpty()) {
//            viewModel.getCampusByName(name).collect { campusList ->
//                Log.d("TAG", "ListOfSearchCampus: produceState: $campusList")
//                value = campusList
//            }
//        } else {
//            value = Campus()
//        }
//    }
    var campus = Campus()
    runBlocking {
        viewModel.getCampusByName(name).collect {
            campus = it
        }
    }
    Log.e("TAG", "CampusDetailScreen: detail: ${campus}")

    Scaffold(
        topBar = {
            CustomToolbar(
                campus.name.toString(),
                navigateBack = { navController.popBackStack() }
            )
        },
        content = { paddingValue ->
            WebViewPage(campus.domains?.get(0).toString(), paddingValue)
        })
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewPage(domain: String, paddingValues: PaddingValues) {
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
    Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { webView },
            update = {
                val url = StringBuilder("https://$domain")
                Log.d("TAG", "WebViewPage: url: $url")
                it.loadUrl(url.toString())
            })

        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}