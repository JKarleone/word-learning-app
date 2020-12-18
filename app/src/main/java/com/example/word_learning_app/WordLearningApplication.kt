package com.example.word_learning_app

import android.app.Application
import com.example.word_learning_app.data.AppDatabase
import com.example.word_learning_app.data.WordCategoryRepository

class WordLearningApplication : Application() {
//    val applicationScope = CoroutineScope(SupervisorJob())

    val db by lazy { AppDatabase.getDatabase(this) }
    val wordCategoryRepository by lazy { WordCategoryRepository(db.wordCategoryDao()) }
}