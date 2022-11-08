package com.sajjadio.laonote.utils.event

import androidx.lifecycle.Observer

class EventObserver<T>(private val onEventUnhandledContent: (T) -> Unit) : Observer<Event<T>> {
    override fun onChanged(event: Event<T>?) {
        event?.getContentIfHandled()?.let { it ->
            onEventUnhandledContent(it)
        }
    }
}