package com.example.word_learning_app.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.word_learning_app.data.Dao.WordCategoryDao
import com.example.word_learning_app.data.Dao.WordDao
import com.example.word_learning_app.data.entity.Word
import com.example.word_learning_app.data.entity.WordCategory

@Database(entities = [Word::class, WordCategory::class], version = 1)
public abstract class AppDatabase : RoomDatabase() {
    
    abstract fun wordDao() : WordDao
    abstract fun wordCategoryDao() : WordCategoryDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context) : AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "word_database"
                ).build()
                INSTANCE = instance

                return instance
            }
        }
    }
}