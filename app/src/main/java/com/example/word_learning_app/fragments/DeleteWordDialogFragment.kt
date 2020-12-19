package com.example.word_learning_app.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.word_learning_app.WordsListActivity

class DeleteWordDialogFragment(private val position: Int): DialogFragment() {
    interface DeleteWordListener {
        fun confirmButtonClicked(position: Int)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(context)
            .setMessage("Удалить слово?")
            .setPositiveButton("Удалить") { _, _ ->
                (context as WordsListActivity).confirmButtonClicked(position)
            }
            .setNegativeButton("Отмена") { dialog, id ->
                dialog.cancel()
            }
            .create()
    }
}