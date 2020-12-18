package com.example.word_learning_app.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "words")
data class Word (
    @PrimaryKey(autoGenerate = true) val id : Long,
    val word : String?,
    val wordCategoryId : Long,
    val transcription : String?,
    val translation : String?,
    @ColumnInfo(name = "repeat_count") val repeatCount : Int,
    @ColumnInfo(name = "time_to_repeat") val timeToRepeat : Long
)