package com.example.word_learning_app.data.repository

import androidx.annotation.WorkerThread
import com.example.word_learning_app.data.Dao.CardDao
import com.example.word_learning_app.data.Dao.WordDao
import com.example.word_learning_app.data.entity.Card
import kotlinx.coroutines.flow.Flow

class CardsRepository(private val cardDao: CardDao) {

    val allCards: Flow<List<Card>> by lazy {
        cardDao.getAllCards()
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(card: Card) {
        cardDao.insert(card)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(card: Card) {
        cardDao.delete(card)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(categoryId: Long?) {
        cardDao.delete(categoryId)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(card: Card) {
        if (card.repeatCount == 10)
            cardDao.delete(card)
        else
            cardDao.update(card)
    }

}