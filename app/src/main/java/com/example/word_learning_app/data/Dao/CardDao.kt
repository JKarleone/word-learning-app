package com.example.word_learning_app.data.Dao

import androidx.room.*
import com.example.word_learning_app.data.entity.Card
import kotlinx.coroutines.flow.Flow

@Dao
interface CardDao {
    @Transaction
    @Query("SELECT * FROM cards WHERE repeat_count < 10 AND time_to_repeat < :time")
    fun getAllCards(time: Long): Flow<List<Card>>

    @Insert
    suspend fun insert(card: Card)

    @Delete
    suspend fun delete(card: Card)

    @Transaction
    @Query("DELETE FROM cards WHERE categoryId = :categoryId")
    suspend fun delete(categoryId: Long?)

    @Update
    suspend fun update(card: Card)
}