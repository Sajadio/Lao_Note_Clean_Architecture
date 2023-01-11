package com.sajjadio.laonote.utils.extension

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import android.webkit.MimeTypeMap
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.sajjadio.laonote.utils.event.Event
import com.sajjadio.laonote.utils.event.EventObserver
import java.text.SimpleDateFormat
import java.util.*


fun View.moveToDestination(
    directions: NavDirections
) {
    this.setOnClickListener {
        Navigation.findNavController(this).navigate(directions)
    }
}

fun <T> LiveData<Event<T>>.observeEvent(owner: LifecycleOwner, function: (T) -> Unit) {
    this.observe(owner, EventObserver { it ->
        function(it)
    })
}

fun LifecycleOwner.launchOnLifecycleScope(execute: suspend () -> Unit) {
    this.lifecycleScope.launchWhenCreated {
        execute()
    }
}

fun getFileExtension(context: Context, uri: Uri?) =
    MimeTypeMap.getSingleton().getExtensionFromMimeType(context.contentResolver.getType(uri!!))


@SuppressLint("SimpleDateFormat")
fun String?.formatDate(from: String, to: String): String? {
    this?.let {
        val formatterFrom = SimpleDateFormat(from, Locale.ENGLISH)
        val formatterTo = SimpleDateFormat(to)
        val parsedDate: Date? = formatterFrom.parse(this)
        return parsedDate?.let { formatterTo.format(it) }.toString()
    }
    return null
}

inline fun <reified A : Activity> Activity.navigateActivity(isFinish: Boolean = false) {
    startActivity(Intent(this, A::class.java))
    if (isFinish)
        this.finish()
}

