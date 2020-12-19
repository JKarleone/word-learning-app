package com.example.word_learning_app.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cards")
data class Card (
    @PrimaryKey(autoGenerate = true)
    val id: Long?,
    val wordId: Long?,
    val word: String?,
    val categoryId: Long?,
    val categoryName: String?,
    val categoryImg: Int?,
    val transcription : String?,
    val translation : String?,
    @ColumnInfo(name = "repeat_count") var repeatCount : Int,
    @ColumnInfo(name = "time_to_repeat") var timeToRepeat : Long
    )