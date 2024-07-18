package com.example.chatwave

import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.storage.FirebaseStorage

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
    HorizontalDivider(
        modifier = Modifier
            .alpha(.5f)
            .padding(top = 10.dp),
        thickness = 2.dp,
        color = clr.r
    )
}

//@Composable
//fun CommanImage(
//    data: String?,
//    modifier: Modifier = Modifier.wrapContentSize(),
//    contentScale: ContentScale = ContentScale.Crop
//) {
//    val context = LocalContext.current
//    val imageBitmap = remember { mutableStateOf<ImageBitmap?>(null) }
//    val isLoading = remember { mutableStateOf(false) }
//
//    if (data != null) {
//        isLoading.value = true
//        FirebaseStorage.getInstance().reference.child("images/$data").getBytes(Long.MAX_VALUE).addOnSuccessListener { bytes ->
//            val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
//            imageBitmap.value = bitmap.asImageBitmap()
//            isLoading.value = false
//            Log.d("ImageLoader", "Image downloaded successfully")
//        }.addOnFailureListener {
//            isLoading.value = false
//            Log.d("ImageLoader", "Error downloading image: $it")
//        }
//    }
//
//    if (isLoading.value) {
//        // Display a progress bar
//        CircularProgressIndicator(modifier = modifier)
//    } else {
//        if (imageBitmap.value != null) {
//            Image(
//                bitmap = imageBitmap.value!!,
//                contentDescription = null,
//                modifier = modifier,
//                contentScale = contentScale
//            )
//            Log.d("ImageLoader", "Image displayed")
//        } else {
//            // Display a default image or a placeholder image
//            Image(
//                bitmap = ImageBitmap.imageResource(id = R.drawable.default_image),
//                contentDescription = null,
//                modifier = modifier,
//                contentScale = contentScale
//            )
//            Log.d("ImageLoader", "Default image displayed")
//        }
//    }
//}


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

@Composable
fun TitleText(txt : String){
    Text(text = txt, fontWeight = FontWeight.Bold, fontSize = 35.sp,
        modifier = Modifier.padding(8.dp))
}