package com.example.chatwave.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.chatwave.DestinationScreens
import com.example.chatwave.R
import com.example.chatwave.clr
import com.example.chatwave.navigateTo


enum class BottomNavigationItem(val icon: Int, val navDestination: DestinationScreens) {

    CHATLIST(R.drawable.chat, DestinationScreens.ChatList),
    STATUSLIST(R.drawable.status, DestinationScreens.StatusList),
    PROFILER(R.drawable.profile, DestinationScreens.Profile)

}

@Composable
fun BottomNavigationMenu(
    selectedItem: BottomNavigationItem,
    navController: NavController
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(bottom = 8.dp)
            .background(clr.r)
    ) {

        for (item in BottomNavigationItem.values()) {
            Image(painter = painterResource(id = item.icon), contentDescription = null,
                modifier = Modifier.size(40.dp).padding(4.dp).weight(1f).clickable {
                    navigateTo(navController,item.navDestination.route)
                },
//                colorFilter = if (item == selectedItem)
//                ColorFilter.tint(color = Color.Black)
//                else
//                ColorFilter.tint(Color.Gray)
            )
        }

    }


}