package com.example.wechat

import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import androidx.fragment.app.Fragment

fun Fragment.showDialog(
    message: String,
    posActionName: String? = null,
    posAction: OnDialogActionClick? = null,
    negActionName: String? = null,
    negAction: OnDialogActionClick? = null,

    ) {
    val builder = AlertDialog.Builder(requireContext())
    builder.setMessage(message)


    if (posActionName != null) {
        builder.setPositiveButton(posActionName) { dialog, _ ->
            dialog.dismiss()
            posAction?.onActionClick()
        }
    }
    if (negActionName != null) {
        builder.setNegativeButton(negActionName) { dialog, _ ->
            dialog.dismiss()
            negAction?.onActionClick()
        }
    }

    val alertDialog = builder.create()
    alertDialog.show()
}

fun Activity.showDialog(
    message: String,
    posActionName: String? = null,
    posAction: OnDialogActionClick? = null,
    negActionName: String? = null,
    negAction: OnDialogActionClick? = null,
    isCancelable: Boolean = true

) {
    val builder = AlertDialog.Builder(this)
    builder.setMessage(message)


    if (posActionName != null) {
        builder.setPositiveButton(posActionName) { dialog, _ ->
            dialog.dismiss()
            posAction?.onActionClick()
        }
    }
    if (negActionName != null) {
        builder.setNegativeButton(negActionName) { dialog, _ ->
            dialog.dismiss()
            negAction?.onActionClick()
        }
    }

    val alertDialog = builder.create()
    alertDialog.setCancelable(isCancelable)
    alertDialog.show()
}

fun Activity.showLoadingProgressBar(
    message: String,
    isCancelable: Boolean = true
): ProgressDialog {
    val alertDialog = ProgressDialog(this)
    alertDialog.setMessage(message)
    alertDialog.setCancelable(isCancelable)

    return alertDialog
}