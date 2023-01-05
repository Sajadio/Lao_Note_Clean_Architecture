package com.sajjadio.laonote.presentation.ui.event

import android.annotation.SuppressLint
import com.sajjadio.laonote.R
import com.sajjadio.laonote.databinding.ItemEventBinding
import com.sajjadio.laonote.domain.model.EventModel
import com.sajjadio.laonote.presentation.base.BaseAdapter
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class EventAdapter @Inject constructor() : BaseAdapter<ItemEventBinding, EventModel>() {
    override val layoutId = R.layout.item_event


    private var onItemClickListener: ((EventModel) -> Unit)? = null
    fun onItemClickListener(listener: (EventModel) -> Unit) {
        onItemClickListener = listener
    }

    @SuppressLint("SimpleDateFormat")
    override fun binder(binding: ItemEventBinding, item: EventModel) {
        binding.apply {
            event = item
            cardView.setOnClickListener {
                onItemClickListener?.let { it(item) }
            }
            executePendingBindings()
        }
    }

}