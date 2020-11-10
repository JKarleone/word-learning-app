package com.example.word_learning_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import com.example.word_learning_app.fragments.CardsFragment
import com.example.word_learning_app.fragments.ListsFragment
import com.example.word_learning_app.fragments.SettingsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.internal.NavigationMenuItemView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val cardsFragment = CardsFragment()
        val listsFragment = ListsFragment()
        val settingsFragment = SettingsFragment()

        makeCurrentFragment(cardsFragment)

        val nav = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        nav.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.ic_cards -> makeCurrentFragment(cardsFragment)
                R.id.ic_lists -> makeCurrentFragment(listsFragment)
                R.id.ic_setting -> makeCurrentFragment(settingsFragment)
            }
            true
        }
    }

    private fun makeCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.current_fragment, fragment)
            commit()
        }
}