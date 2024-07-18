package com.example.chatwave

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chatwave.Screens.ChatListScreen
import com.example.chatwave.Screens.LoginScreen
import com.example.chatwave.Screens.ProfileScreen
import com.example.chatwave.Screens.SignUpScreen
import com.example.chatwave.Screens.StatusScreen
import com.example.chatwave.ui.theme.ChatWaveTheme
import dagger.hilt.android.AndroidEntryPoint

sealed class DestinationScreens(var route : String){
    object SignUp : DestinationScreens("signup")
    object Login : DestinationScreens("login")
    object Profile : DestinationScreens("profile")
    object ChatList : DestinationScreens("chatlist")
    object SingleChat : DestinationScreens("singlechat/{chatId}"){
        fun createRoute(id : String) = "singlechat/$id"
    }
    object StatusList : DestinationScreens("statuslist")
    object SingleStatus : DestinationScreens("singlestatus/{userId}"){
        fun createRoute(userid : String) = "singlestatus/$userid"
    }
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChatWaveTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colorScheme.background
                ) {
                    ChatAppNavigation()

                }
            }
        }
    }

    @Composable
    fun ChatAppNavigation(){
        
        val navController = rememberNavController()
        var vm = hiltViewModel<LCViewModel>()

        NavHost(navController = navController, startDestination = DestinationScreens.Login.route ){

            composable(DestinationScreens.SignUp.route){
                SignUpScreen(navController,vm)
            }
            composable(DestinationScreens.Login.route){
                LoginScreen(navController = navController,vm = vm)
            }
            composable(DestinationScreens.ChatList.route){
                ChatListScreen(navController = navController, vm = vm)
            }
            composable(DestinationScreens.StatusList.route){
                StatusScreen(navController = navController, vm = vm)
            }
            composable(DestinationScreens.Profile.route){
                ProfileScreen(navController = navController, vm = vm)
            }
        }
    }
}

