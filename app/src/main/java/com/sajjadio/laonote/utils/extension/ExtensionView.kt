package com.sajjadio.laonote.utils.extension

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.sajjadio.laonote.R
import com.sajjadio.laonote.utils.TAG
import java.util.*


@SuppressLint("UseCompatLoadingForDrawables")
fun ImageView.loadImage(url: String?) {

    try {
        url?.let {
            Glide.with(this)
                .load(url)
                .error(this.context.getDrawable(R.drawable.ic_outline_image_24))
                .placeholder(this.context.getDrawable(R.drawable.ic_outline_image_24))
                .into(this)
        }
    } catch (e: GlideException) {
        Log.d(TAG, "loadImage: ${e.causes}")
    }

}


fun Context.makeToast(message: String) {
    Toast.makeText(
        this,
        message,
        Toast.LENGTH_SHORT
    ).show()
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