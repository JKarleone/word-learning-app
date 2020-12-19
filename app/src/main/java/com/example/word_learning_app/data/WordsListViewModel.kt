package com.example.word_learning_app.data

import androidx.lifecycle.*
import com.example.word_learning_app.data.repository.WordRepository
import com.example.word_learning_app.data.entity.Word
import com.example.word_learning_app.data.repository.WordCategoryRepository
import kotlinx.coroutines.launch

class WordsListViewModel(private val repository: WordRepository) : ViewModel() {
    val allWords: LiveData<List<Word>> = repository.allWords.asLiveData()

    suspend fun getAllWords(categoryId: Long?): List<Word> =
        repository.getAllWords(categoryId)

    suspend fun insert(word: Word) {
        repository.insert(word)
    }

    suspend fun delete(word: Word) = viewModelScope.launch {
        repository.delete(word)
    }

    suspend fun update(word: Word) = viewModelScope.launch {
        repository.update(word)
    }
}

class WordViewModelFactory(private val repository: WordRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WordsListViewModel::class.java)) {
            @Suppress("UNCHECKED CAST")
            return WordsListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}