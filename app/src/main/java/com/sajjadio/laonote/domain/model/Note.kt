package com.sajjadio.laonote.domain.model

import android.os.Parcelable
import com.sajjadio.laonote.utils.ParentListAdapter
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Note(
    val user_id: String? = null,
    val note_id: String = UUID.randomUUID().toString(),
    val note_title: String? = null,
    val note_subTitle: String? = null,
    val note_description: String? = null,
    val note_image: String? = null,
    val note_webUrl: String? = null,
    val note_color: Int? = 0,
    val font_color: Int? = 0,
    val note_date_created: String? = null,
    val note_last_update: String = "",
    override val item: String = note_id,
) : Parcelable, ParentListAdapter
