package com.example.word_learning_app

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.word_learning_app.data.entity.Word

class WordAdapter(private var context: Context) :
    ListAdapter<Word, WordAdapter.ViewHolder>(WORDS_COMPARATOR) {

    interface OnItemClickListener {
        fun onItemClicked(position: Int): Boolean
        fun onItemLongClicked(position: Int): Boolean
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_word, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(getItem(position))

        holder.itemView.setOnClickListener {
            (context as WordsListActivity).onItemClicked(position)
        }

        holder.itemView.setOnLongClickListener {
            (context as WordsListActivity).onItemLongClicked(position)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val wordText: TextView = itemView.findViewById(R.id.item_word_text)
        private val transcription: TextView = itemView.findViewById(R.id.item_word_transcription)
        private val translation: TextView = itemView.findViewById(R.id.item_word_translation)
        private val wordStatus: TextView = itemView.findViewById(R.id.item_word_status)
        private val statusIcon: ImageView = itemView.findViewById(R.id.item_word_status_icon)

        fun setData(word: Word) {
            wordText.text = word.word
            transcription.text = word.transcription
            translation.text = word.translation
            wordStatus.text = getStatus(word.repeatCount)
            statusIcon.setImageResource(getStatusIcon(word.repeatCount))
        }

        private fun getStatus(status: Int): String =
            when(status) {
                -1 -> "Новое слово"
                10 -> "Выученное слово"
                100 -> "Уже известное слово"
                else -> "Выученное слово (осталось повторов: ${10 - status})"
            }

        private fun getStatusIcon(status: Int): Int =
            when(status) {
                -1 -> R.drawable.icon_status_new_word
                10 -> R.drawable.icon_status_learned
                100 -> R.drawable.icon_status_already_known
                else -> R.drawable.icon_status_learning
            }
    }

    companion object {
        private val WORDS_COMPARATOR = object : DiffUtil.ItemCallback<Word>() {
            override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean {
                return  oldItem.word == newItem.word &&
                        oldItem.transcription == newItem.transcription &&
                        oldItem.translation == newItem.translation &&
                        oldItem.repeatCount == newItem.repeatCount &&
                        oldItem.wordCategoryId == newItem.wordCategoryId &&
                        oldItem.timeToRepeat == oldItem.timeToRepeat
            }
        }
    }

}