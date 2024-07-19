package com.example.chatwave.Screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.chatwave.CommonDivider
import com.example.chatwave.CommonProgressBar
import com.example.chatwave.CommonRow
import com.example.chatwave.DestinationScreens
import com.example.chatwave.LCViewModel
import com.example.chatwave.TitleText
import com.example.chatwave.clr
import com.example.chatwave.navigateTo

@Composable
fun StatusScreen(navController: NavController, vm: LCViewModel) {


    BottomNavigationMenu(
        selectedItem = BottomNavigationItem.STATUSLIST,
        navController = navController
    )
}