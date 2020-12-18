package com.example.word_learning_app.data

import androidx.annotation.WorkerThread
import com.example.word_learning_app.data.Dao.WordCategoryDao
import com.example.word_learning_app.data.entity.WordCategory
import kotlinx.coroutines.flow.Flow

class WordCategoryRepository(private val wordCategoryDao: WordCategoryDao) {

    val allWordCategories: Flow<List<WordCategory>> = wordCategoryDao.getAllWordCategories()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(name: String, img: Int, chosen: Boolean) {
//        wordCategoryDao.insert(name, img, chosen)
        val newCategory = WordCategory( id = null, name = name, img = img, chosen = chosen )
        wordCategoryDao.insert(newCategory)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(wordCategory: WordCategory) {
        wordCategoryDao.delete(wordCategory)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(wordCategory: WordCategory) {
        wordCategoryDao.update(wordCategory)
    }
}