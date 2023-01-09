package com.sajjadio.laonote.presentation.ui.event

import android.annotation.SuppressLint
import com.sajjadio.laonote.R
import com.sajjadio.laonote.databinding.ItemEventBinding
import com.sajjadio.laonote.domain.model.EventModel
import com.sajjadio.laonote.presentation.base.BaseAdapter
import com.sajjadio.laonote.utils.FULL_DATE
import com.sajjadio.laonote.utils.HOUR_AND_MINT
import com.sajjadio.laonote.utils.extension.formatDate
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
            date.text = item.event_date_created.formatDate(FULL_DATE, HOUR_AND_MINT)
            cardView.setOnClickListener {
                onItemClickListener?.let { it(item) }
            }
            executePendingBindings()
        }
    }

}