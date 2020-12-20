package com.example.word_learning_app.data.Dao

import androidx.room.*
import com.example.word_learning_app.data.entity.WordCategoryWithWords
import com.example.word_learning_app.data.entity.Word
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {
    @Transaction
    @Query("SELECT * FROM words WHERE wordCategoryId = :id")
    fun getAllWordsInCategoryById(id: Long?): Flow<List<Word>>

    @Transaction
    @Query("SELECT * FROM words WHERE wordCategoryId = :id")
    suspend fun getAllWords(id: Long?) : List<Word>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(word: Word)

    @Delete
    suspend fun delete(word: Word)

    @Update
    suspend fun update(word: Word)

    @Query("UPDATE words SET repeat_count = :repeatCount WHERE id = :id")
    suspend fun update(id: Long?, repeatCount: Int)

    @Query("UPDATE words SET repeat_count = :repeatCount, time_to_repeat = :timeToRepeat WHERE id = :id")
    suspend fun update(id: Long?, repeatCount: Int, timeToRepeat: Long)
}