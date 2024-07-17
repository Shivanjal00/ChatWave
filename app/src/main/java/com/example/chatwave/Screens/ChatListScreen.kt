package com.example.chatwave.Screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.chatwave.LCViewModel

@Composable
fun ChatListScreen(navController: NavHostController, vm: LCViewModel) {
    
    Text(text = "This is chatlist screen", modifier = Modifier.padding(top = 32.dp))
    BottomNavigationMenu(selectedItem = BottomNavigationItem.CHATLIST, navController = navController)
}