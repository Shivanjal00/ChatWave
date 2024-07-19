package com.example.chatwave.Screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.chatwave.CommonDivider
import com.example.chatwave.Data.Message
import com.example.chatwave.LCViewModel


@Composable
fun SingleChatScreen(navController: NavController, vm: LCViewModel, chatId: String) {


    var reply by rememberSaveable {
        mutableStateOf("")
    }

    val onSendReply = {
        vm.onSendReply(chatId, reply)
        reply = ""
    }

    val chatMessage = vm.chatMessage

    val myUser = vm.userData.value
    var currentChat = vm.chats.value.first { it.chatID == chatId }
    val chatUser =
        if (myUser?.userId == currentChat.user1?.userId) currentChat.user2 else currentChat.user1

    LaunchedEffect(key1 = Unit) {
        vm.populateMessages(chatId)
    }
    BackHandler {
        vm.DePopulteMessage()
    }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        ChatHeader(name = chatUser?.name?: "") {
            navController.popBackStack()
            vm.DePopulteMessage()
        }
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(chatMessage.value) { msg ->
                    val alignment = if (msg.sendBy == myUser?.userId) Alignment.End else Alignment.Start
                    val color = if (msg.sendBy == myUser?.userId) Color(0xff5fa5f8) else Color(0xffce336c)
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalAlignment = alignment
                    ) {
                        Text(
                            text = msg.message?: "",
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(color).padding(12.dp),
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
            ReplyBox(
                reply = reply,
                onReplyChange = { reply = it },
                onSendReply = onSendReply,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .imePadding() // Add this modifier
            )
        }
    }

}

@Composable
fun MessageBox(modifier: Modifier, chatMessage: List<Message>, currentUserId: String) {

    LazyColumn(modifier = modifier) { // Pass the modifier to LazyColumn
        items(chatMessage) { msg ->
            val alignment = if (msg.sendBy == currentUserId) Alignment.End else Alignment.Start
            val color = if (msg.sendBy == currentUserId) Color(0xff5fa5f8) else Color(0xffce336c)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalAlignment = alignment
            ) {
                Text(
                    text = msg.message?: "",
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(color).padding(12.dp),
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

        }
    }

}

@Composable
fun ChatHeader(name: String, onBackClicked: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 32.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(Icons.Rounded.ArrowBack, contentDescription = null,
            modifier = Modifier
                .clickable {
                    onBackClicked.invoke()
                }
                .padding(8.dp))
        Text(text = name, fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 4.dp))
    }
    CommonDivider()
}

@Composable
fun ReplyBox(reply: String, onReplyChange: (String) -> Unit, onSendReply: () -> Unit, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        CommonDivider()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            TextField(value = reply, onValueChange = onReplyChange, maxLines = 3)
            IconButton(onClick = onSendReply) {
                Icon(imageVector = Icons.Filled.Send, contentDescription = "Send")
            }
        }

    }
}