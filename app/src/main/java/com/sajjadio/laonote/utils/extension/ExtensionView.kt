package com.sajjadio.laonote.utils.extension

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.View
import android.view.Window
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
import java.text.SimpleDateFormat
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

fun Context.pickDateTime(): String {
    val currentDateTime = Calendar.getInstance()
    val startYear = currentDateTime.get(Calendar.YEAR)
    val startMonth = currentDateTime.get(Calendar.MONTH)
    val startDay = currentDateTime.get(Calendar.DAY_OF_MONTH)
    val startHour = currentDateTime.get(Calendar.HOUR_OF_DAY)
    val startMinute = currentDateTime.get(Calendar.MINUTE)
    val pickedDateTime = Calendar.getInstance()

    DatePickerDialog(this, { _, year, month, day ->
        TimePickerDialog(this, { _, hour, minute ->
            pickedDateTime.set(year, month, day, hour, minute)
        }, startHour, startMinute, false).show()
    }, startYear, startMonth, startDay).show()

    return pickedDateTime.time.toString().dateFormat()
}

@SuppressLint("SimpleDateFormat")
fun String.dateFormat(): String {
    val sdf = SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH)
    val newDate = SimpleDateFormat("MMM d, yyyy HH:mm:ss")
    val parsedDate: Date? = sdf.parse(this)
    return parsedDate?.let { newDate.format(it) }.toString()
}
