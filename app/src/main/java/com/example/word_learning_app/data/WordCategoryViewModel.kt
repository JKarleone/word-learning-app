package com.example.word_learning_app.data

import androidx.lifecycle.*
import com.example.word_learning_app.data.entity.WordCategory
import com.example.word_learning_app.data.repository.WordCategoryRepository
import kotlinx.coroutines.launch

class WordCategoryViewModel(private val repository: WordCategoryRepository) : ViewModel() {
    val allWordCategories: LiveData<List<WordCategory>> = repository.allWordCategories.asLiveData()

    suspend fun insert(name: String, img: Int, chosen: Boolean) = viewModelScope.launch {
        repository.insert(name, img, chosen)
    }

    suspend fun delete(wordCategory: WordCategory) = viewModelScope.launch {
        repository.delete(wordCategory)
    }

    suspend fun update(wordCategory: WordCategory) = viewModelScope.launch {
        repository.update(wordCategory)
    }
}

class WordCategoryViewModelFactory(private val repository: WordCategoryRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WordCategoryViewModel::class.java)) {
            @Suppress("UNCHECKED CAST")
            return WordCategoryViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}