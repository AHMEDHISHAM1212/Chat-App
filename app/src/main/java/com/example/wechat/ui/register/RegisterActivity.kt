package com.example.wechat.ui.register

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.wechat.home.HomeActivity
import com.example.wechat.Message
import com.example.wechat.R
import com.example.wechat.databinding.ActivityRegisterBinding
import com.example.wechat.ui.login.LoginActivity
import com.example.wechat.showDialog

class RegisterActivity :AppCompatActivity(){
    private var viewBinding: ActivityRegisterBinding?= null
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        observeOnLiveData()
    }

    private fun handelMessage(message: Message?) {
        showDialog(
            message = message?.message?:"Something went wrong..",
            posActionName = message?.posActionName,
            posAction =message?.posActionClick,
            negActionName = message?.negActionName,
            negAction = message?.negActionClick,
            isCancelable = message?.isCancelable?:true
        )
    }

    private fun observeOnLiveData() {
        viewModel.messageLiveData.observe(this,::handelMessage)

        viewModel.event.observe(this,::handelEvents)
    }

    private fun handelEvents(registerViewEvent: RegisterViewEvent?) {

        when(registerViewEvent){
            RegisterViewEvent.NavigateToHome->{
                navigateToHomeActivity()
            }
            RegisterViewEvent.NavigateToLogin->{
                navigateToLogin()
            }

            else -> {}
        }
    }

    private fun navigateToLogin() {
        startActivity(Intent(this,LoginActivity::class.java))
        finish()
    }

    private fun navigateToHomeActivity() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

    private fun initViews() {
        viewBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_register)

        viewBinding?.lifecycleOwner = this
        viewBinding?.vm = viewModel

    }

    override fun onDestroy() {
        super.onDestroy()
        viewBinding = null
    }
}