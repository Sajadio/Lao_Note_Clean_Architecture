package com.sajjadio.laonote.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_table")
data class Note(
    @PrimaryKey(autoGenerate = false)
    val note_id: Int,
    val note_title: String,
    val note_subTitle: String,
    val note_description: String,
    val note_image: String,
    val note_webUrl: String,
    val note_color: String,
    val note_date_created: String,
)
