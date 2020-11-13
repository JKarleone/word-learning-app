package com.example.word_learning_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val navigationController = findNavController(R.id.fragment)

        bottomNavigation.setupWithNavController(navigationController)
    }

    fun showTranslation(view: View) {
        val showTranslationButton = view.findViewById<Button>(R.id.show_translation_button)
        val translationText = view.findViewById<TextView>(R.id.word_translation)

        showTranslationButton.visibility = View.INVISIBLE
//        translationText.visibility = TextView.VISIBLE
    }
}