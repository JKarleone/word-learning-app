package com.example.word_learning_app

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*

class NewWordCategoryActivity : AppCompatActivity() {
    private lateinit var radioGroup: RadioGroup
    private val radioButtons =
            listOf<Int>(R.id.word_category_icon1,
                    R.id.word_category_icon2,
                    R.id.word_category_icon3,
                    R.id.word_category_icon4,
                    R.id.word_category_icon5,
                    R.id.word_category_icon6,
                    R.id.word_category_icon7,
                    R.id.word_category_icon8,
                    R.id.word_category_icon9)

    private var wordCategoryIcon: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_word_category)

        supportActionBar?.hide()

        radioGroup = findViewById(R.id.radio_group)

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
                replyIntent.putExtra(EXTRA_IMG, wordCategoryIcon)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }

    companion object {
        const val EXTRA_NAME = "name"
        const val EXTRA_IMG = "img_id"
    }

    fun onWordCategoryImgClick(view: View) {
        val radioButton = view as RadioButton
        val radioBtnId = radioButton.id
        wordCategoryIcon = getIcon(radioBtnId)
        clearRadioBtnBeside(radioBtnId)
    }

    private fun clearRadioBtnBeside(id: Int) {
        for (btn in radioButtons)
            if (btn != id)
                findViewById<RadioButton>(btn).isChecked = false
    }

    private fun getIcon(radioButtonId: Int) =
            when (radioButtonId) {
                radioButtons[0] -> R.drawable.icon_category1
                radioButtons[1] -> R.drawable.icon_category2
                radioButtons[2] -> R.drawable.icon_category3
                radioButtons[3] -> R.drawable.icon_category4
                radioButtons[4] -> R.drawable.icon_category5
                radioButtons[5] -> R.drawable.icon_category6
                radioButtons[6] -> R.drawable.icon_category7
                radioButtons[7] -> R.drawable.icon_category8
                radioButtons[8] -> R.drawable.icon_category9
                else -> R.drawable.icon_category0
            }
}