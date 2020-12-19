package com.example.word_learning_app.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.word_learning_app.data.entity.Card
import com.example.word_learning_app.data.repository.CardsRepository

class CardsViewModel(private val repository: CardsRepository): ViewModel() {
    val allWordsForCards = repository.allCards.asLiveData()

    suspend fun update(card: Card) {
        repository.update(card)
    }
}