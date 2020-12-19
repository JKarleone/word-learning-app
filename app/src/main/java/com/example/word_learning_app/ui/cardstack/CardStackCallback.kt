package com.example.word_learning_app.ui.cardstack

import androidx.recyclerview.widget.DiffUtil
import com.example.word_learning_app.data.entity.Card

class CardStackCallback(private var old : List<Card>,
                        private var new : List<Card>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return old.size
    }

    override fun getNewListSize(): Int {
        return new.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return old[oldItemPosition] == new[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return old[oldItemPosition] == new[newItemPosition]
    }
}