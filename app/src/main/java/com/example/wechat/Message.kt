package com.example.wechat


data class Message(
val message: String? = null,
val posActionName: String? = null,
val negActionName: String? = null,
val posActionClick: OnDialogActionClick? = null,
val negActionClick: OnDialogActionClick? = null,
val isCancelable: Boolean? = true
)

fun interface OnDialogActionClick {
    fun onActionClick()
}
