package com.example.word_learning_app.fragments

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.word_learning_app.R

class DeleteCategoryDialogFragment(private val fragment: ListsFragment,
                                   private val position: Int) : DialogFragment() {

    interface DeleteCategoryListener {
        fun confirmButtonClicked(position: Int)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(context)
                .setMessage("Удалить категорию?")
                .setPositiveButton("Удалить") { _, _ ->
                    fragment.confirmButtonClicked(position)
                }
                .setNegativeButton("Отмена") { dialog, id ->
                    dialog.cancel()
                }
                .create()
    }
}