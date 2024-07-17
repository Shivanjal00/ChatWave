package com.example.chatwave

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.chatwave.Data.Events
import com.example.chatwave.Data.USER_NODE
import com.example.chatwave.Data.UserData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.google.protobuf.Value
import dagger.hilt.android.lifecycle.HiltViewModel
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class LCViewModel @Inject constructor(
    val auth: FirebaseAuth,
    var db : FirebaseFirestore
) : ViewModel() {


    var inProcess = mutableStateOf(false)
    val eventMutableState = mutableStateOf<Events<String>?>(null)
    var signIn = mutableStateOf(false)
    var userData = mutableStateOf<UserData?>(null)


    init {

        val currentUser = auth.currentUser
        signIn.value = currentUser !=null
        currentUser?.uid?.let {
            getUserData(it)
        }

    }

    fun signUp(name: String, number: String, email: String, password: String) {
        inProcess.value = true
        if(name.isEmpty() or number.isEmpty() or email.isEmpty() or password.isEmpty()){
            handleException(customMessage = "Please fill All fields")
            return
        }

        inProcess.value = true
        db.collection(USER_NODE).whereEqualTo("number" , number).get().addOnSuccessListener {
            if ( it .isEmpty){
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {

                    if (it.isSuccessful) {
                        signIn.value = true
                        createOrUpdateProfile(name,number)
                        Log.d("TAG", "signUp : User Logged In")
                    }else{

                        handleException(it.exception, customMessage = "SignUp Failed !")

                    }
                }
            }else{
                handleException(customMessage = "number Already Exist")
                inProcess.value = false
            }
        }
    }

    fun logIn(email : String, password: String){
        if (email.isEmpty() or password.isEmpty()){
            handleException(customMessage = "Fill both field")
            return
        }else{
            inProcess.value = true
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener{
                if(it.isSuccessful){
                    signIn.value = true
                    inProcess.value = false
                    auth.currentUser?.uid?.let {
                        getUserData(it)
                    }
                }else{
                    handleException(it.exception, customMessage = "Login Failed")
                }
            }
        }
    }

    private fun createOrUpdateProfile(name: String?=null, number: String?=null,imageUrl : String?=null) {
        var uid = auth.currentUser?.uid
        val userData = UserData(
            userId = uid,
            name = name?:userData.value?.name,
            number = number?:userData.value?.number,
            imageUrl = imageUrl?:userData.value?.imageUrl
        )

        uid?.let {
            inProcess.value = true
            db.collection(USER_NODE).document(uid).get().addOnSuccessListener {
                if(it.exists()){
                    //update user data
                }else{
                    db.collection(USER_NODE).document(uid).set(userData)
                    inProcess.value = false
                    getUserData(uid)
                }
            }
                .addOnFailureListener{
                    handleException(it,"Can not Retrieve User")
                }
        }

    }

    private fun getUserData(uid : String) {
        inProcess.value = true
        db.collection(USER_NODE).document(uid).addSnapshotListener{
            value , error->
            if(error != null){
                handleException(error,"Can not retrieve user data")
            }
            if(value != null){
                var user = value.toObject<UserData>()
                userData.value = user
                inProcess.value = false
            }
        }

    }


    fun handleException(exception: Exception?=null,customMessage : String = ""){

        Log.e("ChatWave", "ChatWave exception :",exception)
        exception?.printStackTrace()
        val errorMsg = exception?.localizedMessage?:""
        val message = if(customMessage.isEmpty())errorMsg else customMessage

        eventMutableState.value = Events(message)
        inProcess.value = false

    }

}