package com.sajjadio.laonote.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.sajjadio.laonote.utils.extension.loadImage

@BindingAdapter(value = ["app:uri"])
fun setImage(imageView: ImageView, url: String?) {
    url?.let {
        imageView.loadImage(it)
    }
}
