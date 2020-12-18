package com.example.word_learning_app.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "word_categories")
data class WordCategory(
        @PrimaryKey(autoGenerate = true)
        val id : Long?,
        val name : String,
        val img : Int,
        var chosen : Boolean
        )