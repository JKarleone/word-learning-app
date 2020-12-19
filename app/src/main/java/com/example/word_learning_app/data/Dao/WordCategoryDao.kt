package com.example.word_learning_app.data.Dao

import androidx.room.*
import com.example.word_learning_app.data.entity.WordCategory
import com.example.word_learning_app.data.entity.WordCategoryWithWords
import kotlinx.coroutines.flow.Flow

@Dao
interface WordCategoryDao {
    @Query("SELECT * FROM word_categories")
    fun getAllWordCategories() : Flow<List<WordCategory>>

    @Transaction
    @Query("SELECT * FROM word_categories")
    fun getAllWordCategoriesWithWords() : List<WordCategoryWithWords>

    @Transaction
    @Query("SELECT * FROM word_categories WHERE name = :name ")
    fun getWordCategoryByName(name: String) : WordCategory

    @Transaction
    @Query("SELECT * FROM word_categories WHERE id = :id")
    suspend fun getWordCategoryById(id: Long?): WordCategory

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(wordCategory: WordCategory)

    @Delete
    suspend fun delete(wordCategory: WordCategory)

    @Update
    suspend fun update(wordCategory: WordCategory)
}