package com.example.word_learning_app

import android.app.Activity
import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*

class NewWordActivity : AppCompatActivity() {
    private var categoryName: String = ""
    private var categoryImg: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_word)

        supportActionBar?.hide()

        categoryName = intent.extras?.getString(EXTRA_CATEGORY_NAME).toString()
        categoryImg = intent.extras?.getInt(EXTRA_CATEGORY_IMG)!!

        // Set word category info
        val wordCategoryTextView: TextView = findViewById(R.id.new_word_category_name_text)
        wordCategoryTextView.text = categoryName
        val wordCategoryImg: ImageView = findViewById(R.id.new_word_category_icon)
        wordCategoryImg.setImageResource(categoryImg)

        val editWordView: EditText = findViewById(R.id.edit_word)
        val editWordTranscription: EditText = findViewById(R.id.edit_word_transcription)
        val editWordTranslation: EditText = findViewById(R.id.edit_word_translation)

        val addButton: Button = findViewById(R.id.button_save_word)
        addButton.setOnClickListener {
            val replyIntent = Intent()

            if (TextUtils.isEmpty(editWordView.text) ||
                    TextUtils.isEmpty(editWordTranslation.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            }
            else {
                val word = editWordView.text.toString()
                val transcription = editWordTranscription.text.toString()
                val translation = editWordTranslation.text.toString()

                Toast.makeText(this, "Передаю $word, $transcription, $translation", Toast.LENGTH_SHORT).show()

                replyIntent.putExtra(EXTRA_WORD, word)
                replyIntent.putExtra(EXTRA_TRANSCRIPTION, transcription)
                replyIntent.putExtra(EXTRA_TRANSLATION, translation)

                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }

        val backButton: ImageButton = findViewById(R.id.new_word_button_back)
        backButton.setOnClickListener {
            setResult(Activity.RESULT_CANCELED, Intent())
            finish()
        }
    }

    companion object {
        const val EXTRA_CATEGORY_NAME = "category name"
        const val EXTRA_CATEGORY_IMG = "category img id"
        const val EXTRA_WORD = "word"
        const val EXTRA_TRANSCRIPTION = "transcription"
        const val EXTRA_TRANSLATION = "translation"
    }
}