package com.example.word_learning_app

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton

class NewWordCategoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_word_category)

        supportActionBar?.hide()

        val editWordCategoryView: EditText = findViewById(R.id.edit_word_category)

        val backButton: ImageButton = findViewById(R.id.button_back)
        backButton.setOnClickListener {
            setResult(Activity.RESULT_CANCELED, Intent())
            finish()
        }

        val addButton: Button = findViewById(R.id.button_save_word_category)
        addButton.setOnClickListener {
            val replyIntent = Intent()

            if (TextUtils.isEmpty(editWordCategoryView.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            }
            else {
                val name = editWordCategoryView.text.toString()
                replyIntent.putExtra(EXTRA_NAME, name)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }

    companion object {
        const val EXTRA_NAME = "name"
        const val EXTRA_IMG = "-1"
    }
}