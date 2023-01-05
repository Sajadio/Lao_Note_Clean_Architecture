package com.sajjadio.laonote.domain.model

import android.os.Parcelable
import com.sajjadio.laonote.utils.ParentListAdapter
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class EventModel(
    val event_id: String = UUID.randomUUID().toString(),
    val event_title: String? = null,
    val event_description: String? = null,
    val event_date_created: String? = null,
    override val item: String = event_id,
) : Parcelable, ParentListAdapter
