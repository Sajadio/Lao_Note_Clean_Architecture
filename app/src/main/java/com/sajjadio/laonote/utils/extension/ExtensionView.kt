package com.sajjadio.laonote.utils.extension

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.google.android.material.snackbar.Snackbar
import com.sajjadio.laonote.R
import com.sajjadio.laonote.utils.TAG
import java.util.*


@SuppressLint("UseCompatLoadingForDrawables")
fun ImageView.loadImage(url: String?) {

    try {
        url?.let {
            Glide.with(this)
                .load(url)
                .error(this.context.getDrawable(R.drawable.ic_outline_broken_image_24))
                .placeholder(this.context.getDrawable(R.drawable.ic_outline_image_24))
                .into(this)
        }
    } catch (e: GlideException) {
        Log.d(TAG, "loadImage: ${e.causes}")
    }

}


fun Window.hideSystemUI(view: View) {
    WindowCompat.setDecorFitsSystemWindows(this, false)
    WindowInsetsControllerCompat(this, view).let { controller ->
        controller.hide(WindowInsetsCompat.Type.systemBars())
        controller.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    }
}


fun TextView.handlerClickReSendBtn(isClick: Boolean) {
    this.isClickable = isClick
    if (isClick)
        this.setTextColor(Color.parseColor("#EF6C00"))
    else
        this.setTextColor(Color.parseColor("#FFD180"))
}


fun View.setVisibility(isVisible: Boolean?) {
    isVisible?.let {
        this.visibility = if (isVisible) View.VISIBLE else View.GONE
    }
}

fun View.showSnackbarWithAction(
    msg: String, length: Int, actionMessage: CharSequence?, action: (View) -> Unit,
) {
    val snackbar = Snackbar.make(this, msg, length)
    if (actionMessage != null) {
        snackbar.setAction(actionMessage) { action(this) }.show()
    } else {
        snackbar.show()
    }
}

fun View.showSnackbar(msg: String) {
    val snackbar = sequenceOf(Snackbar.make(this, msg, Toast.LENGTH_SHORT))
}

fun Context.makeToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

@SuppressLint("UseCompatLoadingForDrawables")
fun AppCompatActivity.setToolBar(toolbar: Toolbar, isBack: Boolean = true) {
    this.setSupportActionBar(toolbar)
    supportActionBar!!.setDisplayHomeAsUpEnabled(isBack)
    supportActionBar!!.setDisplayShowHomeEnabled(isBack)
}

fun EditText.showKeyboard() {
    this.requestFocus()
    (this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
        .showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}