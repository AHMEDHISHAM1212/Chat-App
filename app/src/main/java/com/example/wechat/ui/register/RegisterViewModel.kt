package com.example.wechat.ui.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wechat.Message
import com.example.wechat.SessionProvider
import com.example.wechat.SingleLiveEvent
import com.example.wechat.firestoreDB.UsersDao
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class RegisterViewModel : ViewModel(){
    val event = SingleLiveEvent<RegisterViewEvent>()
    val messageLiveData = SingleLiveEvent<Message>()
    val isLoading = MutableLiveData<Boolean>()
    val username = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val passwordConfirmation = MutableLiveData<String>()

    val userNameError = MutableLiveData<String?>()
    val emailError = MutableLiveData<String?>()
    val passwordError = MutableLiveData<String?>()
    val passwordConfirmationError = MutableLiveData<String?>()

    val auth = Firebase.auth
    fun register(){
        if (!validForm())
            return
        isLoading.postValue(true)
        auth.createUserWithEmailAndPassword(email.value!!,password.value!!)
            .addOnCompleteListener{task->
                if(task.isSuccessful){
                  addUserToFireStoreDB(task.result.user?.uid)
                }else {
                    // handle error here
                    isLoading.postValue(false)
                    messageLiveData.postValue(
                        Message(
                            message = task.exception?.localizedMessage
                        )
                    )
                }

            }
    }

    private fun addUserToFireStoreDB(uid: String?) {
        val user = com.example.wechat.model.User(
            id = uid,
            userName = username.value,
            email = username.value
        )

        UsersDao.createUser(user){ task->
            isLoading.postValue(false)
            if (task.isSuccessful){
                // show message and navigate it into home Act
                messageLiveData.postValue(
                    Message(
                        message ="User Registered Successfully",
                        posActionName = "OK",
                        posActionClick = {
                            //save the user on memory per session while the app is running
                            SessionProvider.user = user
                            // navigate to home screen
                            event.postValue(RegisterViewEvent.NavigateToHome)
                        }
                        )
                )
            }else{
                // handle error
            }

        }
    }
    private fun validForm(): Boolean {
        var isValid = true

        if (username.value.isNullOrBlank()) {
            userNameError.postValue("Enter your full name!")
            isValid = false
        } else {
            userNameError.postValue("")
        }

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

        if (passwordConfirmation.value.isNullOrBlank() ||
            passwordConfirmation.value != password.value) {
            passwordConfirmationError.postValue("Password doesn't match")
            isValid = false
        } else {
            passwordConfirmationError.postValue("")
        }

        return isValid

    }
    fun navigateToLogin(){
        event.postValue(RegisterViewEvent.NavigateToLogin)
    }

}