package com.sajjadio.laonote.domain.model

import android.os.Parcelable
import com.sajjadio.laonote.utils.ParentListAdapter
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Task(
    val task_id: String = UUID.randomUUID().toString(),
    val task_title: String? = null,
    val task_description: String? = null,
    val task_webUrl: String? = null,
    val task_date_created: String ? = null,
    override val item: String = task_id,
) : Parcelable, ParentListAdapter
