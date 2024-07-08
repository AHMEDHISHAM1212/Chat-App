package com.example.wechat.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.wechat.R
import com.example.wechat.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private  var viewBinding: ActivityHomeBinding?= null
    private val viewModel: HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
    }
    private fun initViews() {
        viewBinding = DataBindingUtil.setContentView(
            this,R.layout.activity_home)

        viewBinding?.lifecycleOwner = this
        viewBinding?.vm = viewModel
    }

    override fun onDestroy() {
        super.onDestroy()
        viewBinding = null
    }
}