package com.example.word_learning_app.data.Dao

import androidx.room.*
import com.example.word_learning_app.data.entity.WordCategoryWithWords
import com.example.word_learning_app.data.entity.Word
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {
    @Transaction
    @Query("SELECT * FROM word_categories WHERE name = :wordCategoryName LIMIT 1")
    fun getWordCategoryWithWordsByName(wordCategoryName : String) : WordCategoryWithWords

    @Transaction
    @Query("SELECT * FROM words WHERE wordCategoryId = :id")
    fun getAllWordsInCategoryById(id: Long): Flow<List<Word>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(word: Word)

    @Delete
    suspend fun delete(word: Word)

    @Update
    suspend fun update(word: Word)
}