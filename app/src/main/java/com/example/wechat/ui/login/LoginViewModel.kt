package com.example.wechat.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wechat.Message
import com.example.wechat.SessionProvider
import com.example.wechat.SingleLiveEvent
import com.example.wechat.firestoreDB.UsersDao
import com.example.wechat.model.User
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class LoginViewModel : ViewModel(){
    val event = SingleLiveEvent<LoginViewEvent>()
    val messageLiveData = SingleLiveEvent<Message>()
    val isLoading = MutableLiveData<Boolean>()
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    val emailError = MutableLiveData<String>()
    val passwordError = MutableLiveData<String>()

    private val auth = Firebase.auth
    fun login(){
        if (!validForm())
            return
        isLoading.postValue(true)
        auth.signInWithEmailAndPassword(email.value!!,password.value!!)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                   getUserFromFireStore(task.result.user?.uid?:"")
                } else {
                    messageLiveData.postValue(
                        Message(
                            message = task.exception?.localizedMessage
                        )
                    )
                }
            }
    }

    private fun getUserFromFireStore(uid: String?) {
        UsersDao.getUser(uid){task->
            if (task.isSuccessful){
                // save provider
                val user = task.result.toObject(User::class.java)
                SessionProvider.user = user
                //navigate
                messageLiveData.postValue(
                    Message(
                        message = "Logged in Successfully",
                        posActionName = "OK",
                        posActionClick = {
                            event.postValue(LoginViewEvent.NavigateToHome)
                        },
                        isCancelable = false
                    )
                )
            }else{
                // handel error
                messageLiveData.postValue(
                    Message(
                        message = task.exception?.localizedMessage
                    )
                )
            }
        }
    }

    private fun validForm(): Boolean {
        var isValid = true

        if (email.value.isNullOrBlank()) {
            emailError.postValue("Enter your email!")
            isValid = false
        } else {
            emailError.postValue("")
        }

        if (password.value.isNullOrBlank()) {
            passwordError.postValue("Enter password!")
            isValid = false
        } else {
            passwordError.postValue("")
        }

        return isValid
    }

    fun navigateToRegister(){
        event.postValue(LoginViewEvent.NavigateToRegister)
    }
}