package com.example.chatwave.Screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.chatwave.CommonProgressBar
import com.example.chatwave.LCViewModel
import com.example.chatwave.clr

@Composable
fun ProfileScreen(navController: NavController, vm: LCViewModel) {
    val inProgess = vm.inProcess.value
    if (inProgess) {
        CommonProgressBar()
    } else {
        Column {
            ProfileContent()
            BottomNavigationMenu(
                selectedItem = BottomNavigationItem.PROFILER,
                navController = navController
            )
        }
    }
}

@Composable
fun ProfileContent(
    onBack: () -> Unit,
    onSave: () -> Unit
) {

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Back", Modifier.padding(top = 32.dp).clickable {
                onBack.invoke()
            }, color = clr.b)
            Text(text = "Save", Modifier.padding(top = 32.dp).clickable {
                onSave.invoke()
            }, color = clr.b)
        }
    }

}