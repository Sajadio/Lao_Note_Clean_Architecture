package com.sajjadio.laonote.utils.extension

import android.annotation.SuppressLint
import android.content.Context
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
import java.text.ParseException
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


fun Context.getPathFromUri(contentUri: Uri): String? {
    var filePath: String? = null
    val cursor = this.contentResolver.query(contentUri, null, null, null, null)
    if (cursor == null) {
        filePath = contentUri.path
    } else {
        cursor.moveToFirst()
        var index = cursor.getColumnIndex("_data")
        filePath = cursor.getString(index)
        cursor.close()
    }
    return filePath
}


fun getFileExtension(context: Context,uri: Uri?) = MimeTypeMap.getSingleton().getExtensionFromMimeType(context.contentResolver.getType(uri!!))


@SuppressLint("SimpleDateFormat")
fun String?.formatDate(pattern:String): String? {
    this?.let {
        try {
            val formatterFrom = SimpleDateFormat("MMM dd,yyyy  hh:mm aa")
            val formatterTo = SimpleDateFormat(pattern)
            val dateResult: Date? = formatterFrom.parse(this.toString())
           return dateResult?.let { it1 -> formatterTo.format(it1) }
        } catch (e: ParseException) {
            e.printStackTrace()
        }
    }
    return null
}

// "hh:mm aa"