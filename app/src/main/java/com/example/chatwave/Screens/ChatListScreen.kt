package com.example.chatwave.Screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.chatwave.CommonProgressBar
import com.example.chatwave.LCViewModel

@Composable
fun ChatListScreen(navController: NavHostController, vm: LCViewModel) {

    val inProgress = vm.inProcess
    if(inProgress.value){
        CommonProgressBar()
    }else{

    }

    BottomNavigationMenu(selectedItem = BottomNavigationItem.CHATLIST, navController = navController)
}

@Composable
fun FAB(){

}
