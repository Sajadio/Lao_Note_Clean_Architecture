package com.sajjadio.laonote.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_table")
data class Note(
    @PrimaryKey(autoGenerate = false)
    val note_id: String,
    val note_title: String? = null,
    val note_subTitle: String? = null,
    val note_description: String? = null,
    val note_image: String? = null,
    val note_webUrl: String? = null,
    val note_color: String? = null,
    val note_date_created: String,
)
