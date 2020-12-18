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
import com.example.word_learning_app.NewWordCategoryActivity
import com.example.word_learning_app.R
import com.example.word_learning_app.WordLearningApplication
import com.example.word_learning_app.WordCategoryAdapter
import com.example.word_learning_app.data.*
import kotlinx.coroutines.launch

class ListsFragment : Fragment(),
        WordCategoryAdapter.OnItemClickListener,
        WordCategoryAdapter.OnCheckBoxClickListener,
        DeleteCategoryDialogFragment.DeleteCategoryListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: WordCategoryAdapter

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
            Toast.makeText(requireContext(), "Try to add new category", Toast.LENGTH_SHORT).show()
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
        Toast.makeText(requireContext(), "Длинный клик", Toast.LENGTH_SHORT).show()

        val dialogFragment = DeleteCategoryDialogFragment(this, position)
        val manager = requireActivity().supportFragmentManager
        dialogFragment.show(manager, "DeleteCategoryDialog")

        return true
    }

    override fun onItemClicked(position: Int): Boolean {
        Toast.makeText(requireContext(), "Клик", Toast.LENGTH_SHORT).show()

        return true
    }

    override fun confirmButtonClicked(position: Int) {
        Toast.makeText(requireContext(), "Пытаюсь удалить", Toast.LENGTH_SHORT).show()
        val wordCategory = adapter.currentList[position]

        viewLifecycleOwner.lifecycleScope.launch {
            wordCategoryViewModel.delete(wordCategory)
        }

    }

    override fun onCheckBoxClicked(checkBox: CheckBox, position: Int) {
        var wordCategory = adapter.currentList[position]
        wordCategory.chosen = !wordCategory.chosen

        viewLifecycleOwner.lifecycleScope.launch {
            wordCategoryViewModel.update(wordCategory)
        }

        Toast.makeText(requireContext(), "Чекбокс нажали, теперь он: ${wordCategory.chosen}", Toast.LENGTH_SHORT).show()
    }

}