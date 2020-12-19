package com.example.word_learning_app.data.repository

import androidx.annotation.WorkerThread
import com.example.word_learning_app.data.Dao.WordDao
import com.example.word_learning_app.data.entity.Word
import kotlinx.coroutines.flow.Flow

class WordRepository(private val wordCategoryId: Long?,
                     private val wordDao: WordDao) {

    val allWords: Flow<List<Word>> by lazy {
        wordDao.getAllWordsInCategoryById(wordCategoryId)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(word: Word) {
        wordDao.insert(word)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(word: Word) {
        wordDao.delete(word)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(word: Word) {
        wordDao.update(word)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getAllWords(categoryId: Long?): List<Word> =
        wordDao.getAllWords(categoryId)
}