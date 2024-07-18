package com.example.chatwave.Screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.chatwave.CommanImage
import com.example.chatwave.CommonDivider
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
            ProfileContent(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState()).padding(8.dp),
                vm = vm,
                name = "",
                number = "",
                onNameChange = {""},
                onNumberChange = {""},
                onSave = {},
                onBack = {},
                onLogout = {}
            )
            BottomNavigationMenu(
                selectedItem = BottomNavigationItem.PROFILER,
                navController = navController
            )
        }
    }
}

@Composable
fun ProfileContent(
    modifier: Modifier,
    vm: LCViewModel,
    name : String,
    number: String,
    onNameChange:(String)->Unit,
    onNumberChange:(String)->Unit,
    onBack: () -> Unit,
    onSave: () -> Unit,
    onLogout :()->Unit
) {
    val imageUrl = vm.userData.value?.imageUrl

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Back",
                Modifier
                    .padding(top = 32.dp)
                    .clickable {
                        onBack.invoke()
                    }, color = clr.b
            )
            Text(text = "Save",
                Modifier
                    .padding(top = 32.dp)
                    .clickable {
                        onSave.invoke()
                    }, color = clr.b
            )

            CommonDivider()
            ProfileImage(imageUrl = imageUrl,vm = vm,)
            CommonDivider()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(text = "Name",modifier = Modifier.width(100.dp))
                TextField(value = name, onValueChange = onNameChange,
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        focusedContainerColor = Color.Black,
                        unfocusedContainerColor = Color.Black,
                        disabledContainerColor = Color.Black,
                    )
                )

            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(text = "Number",modifier = Modifier.width(100.dp))
                TextField(value = number, onValueChange = onNumberChange,
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                    )
                )
            }
            CommonDivider()
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
                horizontalArrangement = Arrangement.Center) {
                Text(text = "Logout", modifier = Modifier.clickable {onLogout.invoke()})
            }
        }
    }
}

@Composable
fun ProfileImage(imageUrl : String?,vm : LCViewModel) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ){
        uri ->
        uri?.let {
            vm.uploadProfileImage(uri)
        }

    }

    Box(modifier = Modifier.height(intrinsicSize = IntrinsicSize.Min)){

        Column(modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable {
                launcher.launch("image/*")
            }, horizontalAlignment = Alignment.CenterHorizontally)
        {

            Card (shape = CircleShape,
                modifier = Modifier
                    .padding(8.dp)
                    .size(100.dp)){
                CommanImage(data = imageUrl)
            }
            Text(text = "Change profile")
        }
        if(vm.inProcess.value){
            CommonProgressBar()
        }
    }
}
