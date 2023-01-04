package com.sajjadio.laonote.domain.model

import android.os.Parcelable
import com.sajjadio.laonote.utils.ParentListAdapter
import com.sajjadio.laonote.utils.extension.dateFormat
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Note(
    val note_id: String = UUID.randomUUID().toString(),
    val note_title: String? = null,
    val note_subTitle: String? = null,
    val note_description: String? = null,
    val note_image: String? = null,
    val note_webUrl: String? = null,
    val note_color: Int? = 0,
    val font_color: Int? = 0,
    val note_date_created: String = Calendar.getInstance().time.toString().dateFormat(),
    val note_last_update: String? = null,
    override val item: String = note_id,
) : Parcelable, ParentListAdapter
