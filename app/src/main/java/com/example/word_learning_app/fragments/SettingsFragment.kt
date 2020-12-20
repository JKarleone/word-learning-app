package com.example.word_learning_app.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.word_learning_app.ProfileAdapter
import com.example.word_learning_app.R
import com.example.word_learning_app.WordCategoryAdapter
import com.example.word_learning_app.WordLearningApplication
import com.example.word_learning_app.data.WordCategoryViewModel
import com.example.word_learning_app.data.WordCategoryViewModelFactory

class SettingsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProfileAdapter

    private val wordCategoryViewModel: WordCategoryViewModel by viewModels {
        WordCategoryViewModelFactory((activity?.application as WordLearningApplication).wordCategoryRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_settings, container, false)

        recyclerView = root.findViewById(R.id.progress_recyclerview)
        adapter = ProfileAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        wordCategoryViewModel.allWordCategories.observe(requireActivity()) { wordCategories ->
            wordCategories.let { adapter.submitList(it) }
        }

        return root
    }
}