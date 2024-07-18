package com.example.chatwave

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

fun navigateTo(navController: NavController, route: String) {
    navController.navigate(route) {
        popUpTo(route)
        launchSingleTop = true
    }
}

@Composable
fun CommonProgressBar() {
    Row(
        modifier = Modifier
            .alpha(0.5f)
            .background(Color.LightGray)
            .clickable(enabled = false) {}
            .fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator()

    }
}

@Composable
fun CommonDivider(){
    Divider(
        color = clr.r,
        thickness = 1.dp,
        modifier = Modifier.alpha(.3f).padding(top = 8.dp, bottom =8.dp )
    )
}

@Composable
fun CommanImage(){

}


@Composable
fun CheckSignedIn(vm : LCViewModel,navController: NavController){

    val alreadySignedIn = remember { mutableStateOf(false) }

    val signIn = vm.signIn.value

    if(signIn && !alreadySignedIn.value){
        alreadySignedIn.value= true
        navController.navigate(DestinationScreens.ChatList.route){
            popUpTo(0)
        }
    }

}