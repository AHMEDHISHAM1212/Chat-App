package com.example.wechat.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.wechat.home.HomeActivity
import com.example.wechat.Message
import com.example.wechat.databinding.ActivityLoginBinding
import com.example.wechat.ui.register.RegisterActivity
import com.example.wechat.showDialog

class LoginActivity: AppCompatActivity() {
    private var viewBinding: ActivityLoginBinding? =null
    private val viewModel: LoginViewModel by viewModels()
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
                isCancelable = message?.isCancelable?:true)
        }

    private fun observeOnLiveData() {
        viewModel.messageLiveData.observe(this,::handelMessage)
        viewModel.event.observe(this,::handelEvent)

        }

    private fun handelEvent(loginViewEvent: LoginViewEvent?) {
        when(loginViewEvent){
            LoginViewEvent.NavigateToHome->{
                navigateToHome()
            }
            LoginViewEvent.NavigateToRegister->{
                navigateToRegister()
            }

            else -> {}
        }
    }
    private fun navigateToRegister() {
        startActivity(Intent(this,RegisterActivity::class.java))
        finish()
    }

    private fun navigateToHome() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

    private fun initViews() {
        viewBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(viewBinding?.root)

        viewBinding?.lifecycleOwner = this
        viewBinding?.vm = viewModel
    }

    override fun onDestroy() {
        super.onDestroy()
        viewBinding = null
    }
}