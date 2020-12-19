package com.example.word_learning_app

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.word_learning_app.data.WordViewModelFactory
import com.example.word_learning_app.data.WordsListViewModel
import com.example.word_learning_app.data.entity.Word
import com.example.word_learning_app.data.repository.WordRepository
import com.example.word_learning_app.fragments.DeleteWordDialogFragment
import kotlinx.coroutines.launch
import java.util.*

class WordsListActivity : AppCompatActivity(),
    WordAdapter.OnItemClickListener,
    DeleteWordDialogFragment.DeleteWordListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: WordAdapter

    private var categoryId: Long = -1
    private var categoryName: String = ""
    private var categoryImg: Int = 0

    private val newWordActivityRequestCode = 1

    private lateinit var wordsListViewModel: WordsListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_words_list)

        actionBar?.hide()

        categoryId = intent.extras?.getLong(EXTRA_CATEGORY_ID)!!
        categoryName = intent.extras?.getString(EXTRA_CATEGORY_NAME).toString()
        categoryImg = intent.extras?.getInt(EXTRA_CATEGORY_IMG)!!

        val listNameTextView: TextView = findViewById(R.id.list_name)
        listNameTextView.text = "$categoryName $categoryId"

        recyclerView = findViewById(R.id.words_list)
        adapter = WordAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        wordsListViewModel = WordsListViewModel(
            WordRepository(categoryId,
                (application as WordLearningApplication).db.wordDao())
        )

        wordsListViewModel.allWords.observe(this) { words ->
            words.let { adapter.submitList(it) }
        }
    }

    override fun onItemClicked(position: Int): Boolean {
        Toast.makeText(this, "Клик", Toast.LENGTH_SHORT).show()

        return true
    }

    override fun onItemLongClicked(position: Int): Boolean {
        Toast.makeText(this, "Длинный клик", Toast.LENGTH_SHORT).show()

        val dialogFragment = DeleteWordDialogFragment(position)
        dialogFragment.show(supportFragmentManager, "DeleteWordDialog")

        return true
    }

    companion object {
        const val EXTRA_CATEGORY_NAME = "category_name"
        const val EXTRA_CATEGORY_ID = "category_id"
        const val EXTRA_CATEGORY_IMG = "category_img_id"
    }

    override fun confirmButtonClicked(position: Int) {
        // TODO(Обработчик кнопки удаления слова)
        val word = adapter.currentList[position]

        lifecycleScope.launch {
            wordsListViewModel.delete(word)
        }

    }

    fun onBackButtonClick(view: View) {
        finish()
    }

    fun onAddNewWordButtonClicked(view: View) {
        Toast.makeText(this, "Добавляю новое слово", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, NewWordActivity::class.java)
        intent.putExtra(NewWordActivity.EXTRA_CATEGORY_NAME, categoryName)
        intent.putExtra(NewWordActivity.EXTRA_CATEGORY_IMG, categoryImg)

        startActivityForResult(intent, newWordActivityRequestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            val word = data?.getStringExtra(NewWordActivity.EXTRA_WORD)
            val transcription = data?.getStringExtra(NewWordActivity.EXTRA_TRANSCRIPTION)
            val translation = data?.getStringExtra(NewWordActivity.EXTRA_TRANSLATION)

            val newWord = Word( id = null,
                                word = word,
                                wordCategoryId = categoryId,
                                transcription = transcription,
                                translation = translation,
                                repeatCount = -1,
                                timeToRepeat = Calendar.getInstance().timeInMillis )

            lifecycleScope.launch {
                wordsListViewModel.insert(newWord)
            }

            Toast.makeText(
                this, "Новое слово: $word \n" +
                                    "Транскрипция: $transcription \n" +
                                    "Перевод: $translation",
                Toast.LENGTH_SHORT
            ).show()
        }
        else {
            Toast.makeText(this, "Ничего не добалено", Toast.LENGTH_SHORT).show()
        }
    }
}