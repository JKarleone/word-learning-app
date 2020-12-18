package com.example.word_learning_app.data.Dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.word_learning_app.data.WordCategoryWithWords

@Dao
interface WordDao {
    @Transaction
    @Query("SELECT * FROM word_categories WHERE name = :wordCategoryName LIMIT 1")
    fun getWordCategoryWithWordsByName(wordCategoryName : String) : WordCategoryWithWords
}