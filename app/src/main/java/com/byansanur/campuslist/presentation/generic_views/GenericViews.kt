package com.byansanur.campuslist.presentation.generic_views

import androidx.annotation.MainThread
import androidx.annotation.UiThread
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.byansanur.campuslist.R
import com.byansanur.campuslist.domain.model.Campus
import com.byansanur.campuslist.ui.theme.BlackTransparent
import com.byansanur.campuslist.ui.theme.Purple40

@UiThread
@MainThread
@Composable
fun StartDefaultLoader() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(color = BlackTransparent)
    ) {
        CircularProgressIndicator(color = Purple40)
    }
}

@UiThread
@MainThread
@Composable
fun ShowDialog(
    title: String?,
    message: String?,
    onClickOk: () -> Unit
) {
    val openDialog = remember { mutableStateOf(true) }
    if (openDialog.value)
        AlertDialog(
            onDismissRequest = { openDialog.value = false },
            confirmButton = {
                TextButton(onClick = {
                    openDialog.value = false
                    onClickOk.invoke()
                }) { Text(text = stringResource(id = R.string.ok)) }
            },
            title = { title?.let { Text(text = it) } },
            text = { message?.let { Text(text = it) } },
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false
            ),
        )
}

@UiThread
@MainThread
@Composable
fun CampusItemView(
    campus: Campus,
    listener: (Campus) -> Unit
) {
    Card(
        modifier = Modifier
            .height(125.dp)
            .fillMaxWidth()
            .padding(5.dp)
            .clickable {
                listener(campus)
            },
        shape = RoundedCornerShape(9.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant), // Opaque background
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = null,
            )
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .wrapContentWidth()
                    .fillMaxHeight()
                    .padding(start = 15.dp),
            ) {
                Column {
                    Text(
                        campus.name.toString(),
                        style = TextStyle(fontSize = 15.sp, textAlign = TextAlign.Left),
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        campus.country.toString(),
                        style = TextStyle(fontSize = 15.sp, textAlign = TextAlign.Left),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}