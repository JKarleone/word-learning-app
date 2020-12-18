package com.example.word_learning_app.data

import androidx.room.Embedded
import androidx.room.Relation
import com.example.word_learning_app.data.entity.Word
import com.example.word_learning_app.data.entity.WordCategory

data class WordCategoryWithWords(
        @Embedded val wordCategory : WordCategory,
        @Relation(
        parentColumn = "id",
        entity = Word::class,
        entityColumn = "wordCategoryId"
    )
    val words : List<Word>
)