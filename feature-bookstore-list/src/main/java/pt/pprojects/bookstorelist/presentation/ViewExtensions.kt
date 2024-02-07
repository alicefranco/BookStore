package pt.pprojects.bookstorelist.presentation

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import pt.pprojects.bookstorelist.R

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun Activity?.showDialog(
    title: String,
    message: String?,
    positiveAction: () -> Unit,
    negativeAction: () -> Unit
) {
    this?.let {
        val mAlertDialog = AlertDialog.Builder(this)
        mAlertDialog.setTitle(title)
        mAlertDialog.setMessage(message ?: "")
        mAlertDialog.setPositiveButton(getString(R.string.button_yes)) { _, _ ->
            positiveAction()
        }
        mAlertDialog.setNegativeButton(getString(R.string.button_no)) { _, _ ->
            negativeAction()
        }
        mAlertDialog.show()
    }
}

fun ImageView.setOptionalImage(
    resource: String?,
    context: Context
) {
    resource?.let { resource ->
        if (resource.isNotEmpty()) {
            Glide.with(context)
                .load(resource)
                .placeholder(R.mipmap.ic_launcher_foreground)
                .into(this)
        }
    }
}