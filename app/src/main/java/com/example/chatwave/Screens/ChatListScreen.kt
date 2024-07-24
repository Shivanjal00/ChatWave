package com.example.chatwave.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.chatwave.CommonProgressBar
import com.example.chatwave.CommonRow
import com.example.chatwave.DestinationScreens
import com.example.chatwave.LCViewModel
import com.example.chatwave.TitleText
import com.example.chatwave.clr
import com.example.chatwave.navigateTo

@Composable
fun ChatListScreen(navController: NavHostController, vm: LCViewModel) {

    val inProgress = vm.inProcessChats
    if (inProgress.value) {
        CommonProgressBar()
    } else {
        val chats = vm.chats.value
        val userData = vm.userData.value
        val showDialog = remember {
            mutableStateOf(false)
        }
        val onFabClick: () -> Unit = { showDialog.value = true }
        val onDismiss: () -> Unit = { showDialog.value = false }
        val onAddChat: (String) -> Unit = {
            vm.onAddChat(it)
            showDialog.value = false
        }

        Scaffold(
            floatingActionButton = {
                FloatingActionButton(
                    onClick = onFabClick,
                    containerColor = clr.r,
                    shape = CircleShape,
                    modifier = Modifier.padding(bottom = 40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Add,
                        contentDescription = null,
                        tint = clr.b
                    )
                }
            },
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                ) {
                    TitleText(txt = "Chats")

                    if (chats.isEmpty()) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(text = "No Chats Available")
                        }
                    } else {
                        LazyColumn(modifier = Modifier.weight(1f)) {
                            items(chats) { chat ->
                                val chatuser = if (chat.user1?.userId == userData?.userId){
                                    chat.user2
                                }else{
                                    chat.user2
                                }
                                CommonRow(imageUrl = chatuser?.imageUrl, name = chatuser?.name) {
                                    chat.chatID?.let {
                                        navigateTo(navController, DestinationScreens.SingleChat.createRoute(id = it))
                                    }
                                }
                            }
                        }
                    }

                    BottomNavigationMenu(
                        selectedItem = BottomNavigationItem.CHATLIST,
                        navController = navController
                    )

                }
            }

        )

        if (showDialog.value) {
            AddChatDialog(
                onDismiss = onDismiss,
                onAddChat = onAddChat
            )
        }
    }
}

@Composable
fun AddChatDialog(
    onDismiss: () -> Unit,
    onAddChat: (String) -> Unit
) {
    val addChatNumber = remember {
        mutableStateOf("")
    }
    AlertDialog(onDismissRequest = {
        onDismiss.invoke()
        addChatNumber.value = ""
    },
        confirmButton = {
            Button(onClick = { onAddChat(addChatNumber.value) }) {
                Text(text = "Add Chat")
            }
        },
        title = { Text(text = "Add Chat") },
        text = {
            OutlinedTextField(
                value = addChatNumber.value,
                onValueChange = { addChatNumber.value = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }
    )
}











