package com.example.word_learning_app.data.Dao

import androidx.room.*
import com.example.word_learning_app.data.entity.WordCategory
import com.example.word_learning_app.data.WordCategoryWithWords
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

    @Query("INSERT INTO word_categories (name, img, chosen) VALUES (:name, :img, :chosen)")
    fun insert(name: String, img: Int, chosen: Boolean)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(wordCategory: WordCategory)

    @Delete
    suspend fun delete(wordCategory: WordCategory)
}