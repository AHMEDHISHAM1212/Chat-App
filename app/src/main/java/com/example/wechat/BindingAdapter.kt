package com.example.wechat

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("app:Error")
fun bindErrorOnTextInputLayout(
    textInputLayout: TextInputLayout,
    errorMessage: String?
){
    textInputLayout.error = errorMessage
}