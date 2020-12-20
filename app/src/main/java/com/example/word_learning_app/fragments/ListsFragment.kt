package com.example.word_learning_app.fragments

import android.app.Activity
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.word_learning_app.*
import com.example.word_learning_app.data.*
import com.example.word_learning_app.data.entity.Card
import com.example.word_learning_app.data.repository.CardsRepository
import com.example.word_learning_app.data.repository.WordRepository
import kotlinx.coroutines.launch

class ListsFragment : Fragment(),
        WordCategoryAdapter.OnItemClickListener,
        WordCategoryAdapter.OnCheckBoxClickListener,
        DeleteCategoryDialogFragment.DeleteCategoryListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: WordCategoryAdapter

    private lateinit var wordsListViewModel: WordsListViewModel

    private val newWordCategoryActivityRequestCode = 1
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
        val root = inflater.inflate(R.layout.fragment_lists, container, false)

        recyclerView = root.findViewById(R.id.word_category_list)
        adapter = WordCategoryAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        wordCategoryViewModel.allWordCategories.observe(requireActivity()) { wordCategories ->
            wordCategories.let { adapter.submitList(it) }
        }

        val addingCategoryLayout: ConstraintLayout = root.findViewById(R.id.adding_category_btn)
        addingCategoryLayout.setOnClickListener {
            val intent = Intent(requireContext(), NewWordCategoryActivity::class.java)
            startActivityForResult(intent, newWordCategoryActivityRequestCode)
        }

        return root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            val categoryName = data?.getStringExtra(NewWordCategoryActivity.EXTRA_NAME)
            val imgIndex = data?.getIntExtra(NewWordCategoryActivity.EXTRA_IMG, -1)

            viewLifecycleOwner.lifecycleScope.launch {
                wordCategoryViewModel.insert(categoryName!!, imgIndex!!, false)
            }

            Toast.makeText(requireContext(), "Добавлен новый список: $categoryName", Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(requireContext(), "Ничего не добалено", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onItemLongClicked(position: Int): Boolean {
        val dialogFragment = DeleteCategoryDialogFragment(this, position)
        val manager = requireActivity().supportFragmentManager
        dialogFragment.show(manager, "DeleteCategoryDialog")

        return true
    }

    override fun onItemClicked(position: Int): Boolean {
        var wordCategory = adapter.currentList[position]

        val intent = Intent(requireContext(), WordsListActivity::class.java)
        intent.putExtra(WordsListActivity.EXTRA_CATEGORY_NAME, wordCategory.name)
        intent.putExtra(WordsListActivity.EXTRA_CATEGORY_ID, wordCategory.id)
        intent.putExtra(WordsListActivity.EXTRA_CATEGORY_IMG, wordCategory.img)
        intent.putExtra(WordsListActivity.EXTRA_CATEGORY_CHOSEN, wordCategory.chosen)

        startActivity(intent)

        return true
    }

    override fun confirmButtonClicked(position: Int) {
        Toast.makeText(requireContext(), "Удалено", Toast.LENGTH_SHORT).show()
        val wordCategory = adapter.currentList[position]

        viewLifecycleOwner.lifecycleScope.launch {

            // Delete all words
            wordsListViewModel = WordsListViewModel(
                WordRepository(wordCategory.id,
                    (requireActivity().application as WordLearningApplication).db.wordDao())
            )
            val words = wordsListViewModel.getAllWords(wordCategory.id)

            if (wordCategory.chosen) {
                val cardsRepository = CardsRepository((requireActivity().application as WordLearningApplication).db.cardDao())
                cardsRepository.delete(wordCategory.id)
            }

            for (word in words) {
                wordsListViewModel.delete(word)
            }

            // Delete category
            wordCategoryViewModel.delete(wordCategory)
        }

    }

    override fun onCheckBoxClicked(checkBox: CheckBox, position: Int) {
        var wordCategory = adapter.currentList[position]
        wordCategory.chosen = !wordCategory.chosen

        viewLifecycleOwner.lifecycleScope.launch {
            wordCategoryViewModel.update(wordCategory)

            val cardsRepository = CardsRepository((requireActivity().application as WordLearningApplication).db.cardDao())
            if (!wordCategory.chosen)
                cardsRepository.delete(wordCategory.id)
            else {
                val wordRepository = WordRepository(wordCategory.id, (requireActivity().application as WordLearningApplication).db.wordDao())
                val words = wordRepository.getAllWords(wordCategory.id)
                for (word in words) {
                    if (word.repeatCount < 10)
                        cardsRepository.insert(Card(
                                id = null,
                                wordId = word.id,
                                word = word.word,
                                categoryId = wordCategory.id,
                                categoryName = wordCategory.name,
                                categoryImg = wordCategory.img,
                                transcription = word.transcription,
                                translation = word.translation,
                                repeatCount = word.repeatCount,
                                timeToRepeat = word.timeToRepeat
                        ))
                }
            }
        }
    }

}