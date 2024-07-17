package com.example.chatwave.Screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.chatwave.LCViewModel

@Composable
fun StatusScreen(navController: NavController,vm :LCViewModel) {
    BottomNavigationMenu(selectedItem = BottomNavigationItem.STATUSLIST, navController = navController)
}