package com.sajjadio.laonote.utils

import android.annotation.SuppressLint
import android.os.Build
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.makeramen.roundedimageview.RoundedImageView
import com.sajjadio.laonote.presentation.base.BaseAdapter
import com.sajjadio.laonote.utils.extension.formatDate
import com.sajjadio.laonote.utils.extension.loadImage
import kotlinx.android.synthetic.main.color_picker_dialog.view.*
import java.util.*


@BindingAdapter(value = ["app:items"])
fun setRecyclerItems(view: RecyclerView, items: List<ParentListAdapter>?) {
    items?.let {
        (view.adapter as BaseAdapter<*, *>?)
            ?.updateItems(items)
    }
}

@BindingAdapter(value = ["app:setImage"])
fun setImage(imageView: RoundedImageView, url: String?) {
    url?.let {
        imageView.loadImage(it)
    }
}

@BindingAdapter(value = ["app:setText"])
fun setText(view: View, text: String?) {
    text?.let {
        if (view is TextView) {
            view.isVisible = text.isNotEmpty()
            view.text = text
        } else {
            (view as EditText).isVisible = text.isNotEmpty()
            view.hint = text
        }
    }
}

@BindingAdapter(value = ["app:setBackgroundColor"])
fun setBackgroundColor(view: View, color: Int?) {
    color?.let { view.setBackgroundResource(color) }
}

@RequiresApi(Build.VERSION_CODES.M)
@BindingAdapter(value = ["app:setFontColor"])
fun setFontColor(view: View, color: Int?) {
    color?.let {
        if (view is MaterialTextView)
            view.setTextColor(view.context.getColor(color))
        else {
            (view as AppCompatEditText).setTextColor(view.context.getColor(color))
            view.setHintTextColor(view.context.getColor(color))
        }
    }
}


@SuppressLint("SimpleDateFormat")
@RequiresApi(Build.VERSION_CODES.M)
@BindingAdapter(value = ["app:formatDate"])
fun formatDate(view: TextView, date: String?) {
    view.text = date.formatDate(FULL_DATE, HOUR_AND_MINT)
}


