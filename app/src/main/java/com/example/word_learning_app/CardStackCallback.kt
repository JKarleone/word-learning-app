package com.example.word_learning_app

import androidx.recyclerview.widget.DiffUtil

class CardStackCallback(private var old : List<CardModel>,
                        private var new : List<CardModel>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return old.size
    }

    override fun getNewListSize(): Int {
        return new.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return old[oldItemPosition].word == new[newItemPosition].word &&
                old[oldItemPosition].transcription == new[newItemPosition].transcription &&
                old[oldItemPosition].translation == new[newItemPosition].translation &&
                old[oldItemPosition].category == new[newItemPosition].category &&
                old[oldItemPosition].listName == new[newItemPosition].listName
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return old[oldItemPosition] == new[newItemPosition]
    }
}